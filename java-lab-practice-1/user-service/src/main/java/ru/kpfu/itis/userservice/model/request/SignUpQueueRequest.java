package ru.kpfu.itis.userservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpQueueRequest {
    private String firstName;
    private String lastName;
    private String login;
    private String password;
}