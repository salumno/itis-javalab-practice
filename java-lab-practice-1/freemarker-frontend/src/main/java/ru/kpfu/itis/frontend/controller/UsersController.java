package ru.kpfu.itis.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.frontend.dto.UserDto;
import ru.kpfu.itis.frontend.utils.TokenUtils;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final String USERS_API_URL = "http://localhost:8081/api/user-service/users";

    private final RestTemplate restTemplate;
    private final TokenUtils tokenUtils;

    @Autowired
    public UsersController(final RestTemplate restTemplate, final TokenUtils tokenUtils) {
        this.restTemplate = restTemplate;
        this.tokenUtils = tokenUtils;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        final HttpEntity httpEntity = new HttpEntity(tokenUtils.getAuthorizationHeaders());
        return restTemplate.exchange(USERS_API_URL, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<UserDto>>() {});
    }
}
