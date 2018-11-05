package ru.kpfu.itis.authservice.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.authservice.security.authentication.JwtAuthentication;
import ru.kpfu.itis.authservice.security.details.UserDetailsImpl;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(jwtAuthentication.getName())
                    .getBody();
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Invalid token");
        }

        UserDetails userDetails = new UserDetailsImpl(
                Long.parseLong(body.getId()),
                body.get("role").toString(),
                body.get("sub").toString()
        );

        jwtAuthentication.setUserDetails(userDetails);
        jwtAuthentication.setAuthenticated(true);
        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return JwtAuthentication.class.equals(authenticationClass);
    }
}
