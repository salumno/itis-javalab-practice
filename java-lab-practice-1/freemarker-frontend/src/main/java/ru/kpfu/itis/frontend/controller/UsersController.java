package ru.kpfu.itis.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.frontend.dto.UserDto;
import ru.kpfu.itis.frontend.model.DisableUserRequest;
import ru.kpfu.itis.frontend.utils.TokenUtils;

import java.util.List;

import static java.util.Collections.singletonList;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final String USERS_API_URL = "http://localhost:8081/api/user-service/users";
    private final String AUTH_API_URL = "http://localhost:8081/api/auth-service/users";

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

    @GetMapping("/ban/{userLogin}")
    public ResponseEntity banUser(@PathVariable String userLogin) {
        DisableUserRequest disableUserRequest = new DisableUserRequest();
        disableUserRequest.setUserLogin(userLogin);
        HttpHeaders headers = tokenUtils.getAuthorizationHeaders();
        headers.setAccept(singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<DisableUserRequest> entity = new HttpEntity<>(disableUserRequest, headers);
        return restTemplate.exchange(AUTH_API_URL + "/disable", HttpMethod.POST, entity, Object.class);
    }
}
