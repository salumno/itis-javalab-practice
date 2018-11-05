package ru.kpfu.itis.frontend.controller;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.frontend.dto.AuthTokenDto;
import ru.kpfu.itis.frontend.model.LoginParameters;
import ru.kpfu.itis.frontend.utils.TokenUtils;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final String AUTH_API_URL = "http://localhost:8081/api/auth-service/auth/login";

    private final RestTemplate restTemplate;
    private final TokenUtils tokenUtils;

    public LoginController(final RestTemplate restTemplate, final TokenUtils tokenUtils) {
        this.restTemplate = restTemplate;
        this.tokenUtils = tokenUtils;
    }

    @PostMapping
    public String login(@Valid final LoginParameters parameters) {
        final AuthTokenDto authTokenDto = restTemplate.exchange(AUTH_API_URL, HttpMethod.POST, null, AuthTokenDto.class, parameters).getBody();
        if (authTokenDto.getValue() == null) {
            throw new IllegalArgumentException();
        }
        TokenUtils.setToken(authTokenDto.getValue());
        return "users";
    }
}
