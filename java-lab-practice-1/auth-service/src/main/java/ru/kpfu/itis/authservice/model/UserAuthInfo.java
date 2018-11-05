package ru.kpfu.itis.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import ru.kpfu.itis.authservice.security.model.Role;
import ru.kpfu.itis.authservice.security.model.Status;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthInfo {
    private Long userId;

    private String login;

    private Status status;

    private Role role;
}
