package ru.kpfu.itis.authservice.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DisableUserRequest {
    @NotNull
    private Long userId;
}
