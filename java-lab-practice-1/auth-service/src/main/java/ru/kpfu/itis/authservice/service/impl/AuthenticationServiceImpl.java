package ru.kpfu.itis.authservice.service.impl;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.authservice.model.AuthToken;
import ru.kpfu.itis.authservice.model.UserAuthInfo;
import ru.kpfu.itis.authservice.model.UserCredentials;
import ru.kpfu.itis.authservice.model.UserPersonalData;
import ru.kpfu.itis.authservice.repository.UserPersonalDataRepository;
import ru.kpfu.itis.authservice.security.model.Role;
import ru.kpfu.itis.authservice.security.model.Status;
import ru.kpfu.itis.authservice.service.AuthenticationService;
import ru.kpfu.itis.authservice.service.TokenStatusService;

import java.time.Instant;
import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final PasswordEncoder encoder;
    private final TokenStatusService tokenStatusService;
    private final UserPersonalDataRepository userPersonalDataRepository;

    @Autowired
    public AuthenticationServiceImpl(final PasswordEncoder encoder,
                                     final TokenStatusService tokenStatusService,
                                     final UserPersonalDataRepository userPersonalDataRepository) {
        this.encoder = encoder;
        this.tokenStatusService = tokenStatusService;
        this.userPersonalDataRepository = userPersonalDataRepository;
    }

    @Override
    public AuthToken signIn(final UserCredentials credentials) {
        final String login = credentials.getLogin();
        final String password = credentials.getPassword();

        final UserPersonalData user = userPersonalDataRepository.findByLogin(login).orElseThrow(()
                -> new BadCredentialsException("User is not found by login <" + login + ">"));

        if (isUserBanned(user.getStatus())) {
            throw new BadCredentialsException("User is banned");
        }

        if (encoder.matches(password, user.getHashPassword())) {
            final Date expDate = Date.from(Instant.now().plusMillis(expiration));
            final String token = Jwts.builder()
                    .claim("role", user.getRole())
                    .claim("status", user.getStatus())
                    .setId(user.getId().toString())
                    .setSubject(user.getLogin())
                    .setExpiration(expDate)
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
            tokenStatusService.saveTokenForUser(user, expDate);
            return new AuthToken(token);
        } else {
            throw new BadCredentialsException("User login/password incorrect");
        }
    }

    @Override
    public UserAuthInfo verify(String authToken) {
        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken)
                    .getBody();
            UserPersonalData user = userPersonalDataRepository.findByLogin(body.getSubject()).orElseThrow(
                    () -> new BadCredentialsException("User is not found by login <" + body.getSubject() + ">"));

            final UserAuthInfo userAuthInfo = UserAuthInfo.builder()
                    .userId(user.getId())
                    .login(user.getLogin())
                    .status(user.getStatus())
                    .role(user.getRole())
                    .build();
            // TODO: Why should we have to use Redis for user status validation instead of common DB request?
            if (!isUserBanned(user.getStatus())) {
                return userAuthInfo;
            } else {
                throw new AuthenticationServiceException("User with id " + userAuthInfo.getUserId() + " is not active");
            }

        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Invalid token");
        }
    }

    private boolean isUserBanned(final Status status) {
        return Status.BANNED.equals(status);
    }
}
