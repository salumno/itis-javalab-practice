package ru.kpfu.itis.authservice.model;

import lombok.Data;

@Data
public class UserCredentials {
    private String login;
    private String password;
}
