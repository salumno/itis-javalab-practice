package ru.kpfu.itis.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.authservice.model.AuthToken;
import ru.kpfu.itis.authservice.model.UserAuthInfo;
import ru.kpfu.itis.authservice.model.UserCredentials;
import ru.kpfu.itis.authservice.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthToken> signIn(@Valid @RequestBody final UserCredentials credentials) {
        return ResponseEntity.ok(authenticationService.signIn(credentials));
    }

    @PostMapping("/verify")
    public ResponseEntity<UserAuthInfo> verify(@RequestBody final String authToken) {
        return ResponseEntity.ok(authenticationService.verify(authToken));
    }
}
