package ru.kpfu.itis.frontend.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginParameters {
    @NotNull
    private String login;
    @NotNull
    private String password;
}
