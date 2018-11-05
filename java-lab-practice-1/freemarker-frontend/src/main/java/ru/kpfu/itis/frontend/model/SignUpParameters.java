package ru.kpfu.itis.frontend.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SignUpParameters {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String login;
    @NotNull
    private String password;
}
