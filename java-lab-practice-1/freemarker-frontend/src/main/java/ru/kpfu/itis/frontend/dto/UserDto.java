package ru.kpfu.itis.frontend.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String photoUrl;
}
