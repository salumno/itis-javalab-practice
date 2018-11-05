package ru.kpfu.itis.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.frontend.amqp.sender.SignUpRequestSender;
import ru.kpfu.itis.frontend.model.SignUpParameters;

import javax.validation.Valid;

@RestController
@RequestMapping("/sign-up")
public class SignUpController {

    private final SignUpRequestSender signUpRequestSender;

    @Autowired
    public SignUpController(SignUpRequestSender signUpRequestSender) {
        this.signUpRequestSender = signUpRequestSender;
    }

    @PostMapping
    public ResponseEntity signUpUser(@Valid @RequestBody final SignUpParameters parameters) {
        signUpRequestSender.sendSignUpRequest(parameters);
        return ResponseEntity.ok().build();
    }
}
