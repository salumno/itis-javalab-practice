package ru.kpfu.itis.frontend.controller;

import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.frontend.dto.AuthTokenDto;
import ru.kpfu.itis.frontend.model.LoginParameters;
import ru.kpfu.itis.frontend.utils.TokenUtils;

import javax.validation.Valid;

import static java.util.Collections.singletonList;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final String AUTH_API_URL = "http://zuul-service:8081/api/auth-service/auth/login";

    private final RestTemplate restTemplate;

    public LoginController(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    @SneakyThrows
    public String login(@Valid final LoginParameters parameters) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<LoginParameters> entity = new HttpEntity<>(parameters, headers);

        final AuthTokenDto authTokenDto = restTemplate.exchange(AUTH_API_URL, HttpMethod.POST, entity, AuthTokenDto.class).getBody();
        if (authTokenDto.getValue() == null) {
            throw new IllegalArgumentException();
        }
        TokenUtils.setToken(authTokenDto.getValue());
        return "redirect:/users-page";
    }
}
