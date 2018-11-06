package ru.kpfu.itis.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.authservice.model.DisableUserRequest;
import ru.kpfu.itis.authservice.service.TokenStatusService;
import ru.kpfu.itis.authservice.service.UserServeService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("users")
public class UserServeController {

    private final TokenStatusService tokenStatusService;
    private final UserServeService userServeService;


    @Autowired
    public UserServeController(TokenStatusService tokenStatusService, UserServeService userServeService) {
        this.tokenStatusService = tokenStatusService;
        this.userServeService = userServeService;
    }

    @PostMapping("/disable")
    public ResponseEntity disableUser(@Valid @RequestBody final DisableUserRequest request) {
        tokenStatusService.disableUser(request.getUserLogin());
        userServeService.disableUser(request.getUserLogin());
        return ResponseEntity.ok().build();
    }
}
