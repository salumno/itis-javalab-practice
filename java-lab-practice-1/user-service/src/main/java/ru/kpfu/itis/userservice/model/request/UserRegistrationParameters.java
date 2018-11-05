package ru.kpfu.itis.userservice.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegistrationParameters {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String photoUrl;
}
