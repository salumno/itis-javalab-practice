package ru.kpfu.itis.authservice.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kpfu.itis.authservice.model.User;
import ru.kpfu.itis.authservice.model.UserPersonalData;
import ru.kpfu.itis.authservice.security.model.Role;
import ru.kpfu.itis.authservice.security.model.Status;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private UserPersonalData user;

    public UserDetailsImpl(UserPersonalData user) {
        this.user = user;
    }

    public UserDetailsImpl(final Long id, final String role, final String login) {
        user = UserPersonalData.builder()
                .id(id)
                .role(Role.valueOf(role))
                .login(login)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final GrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole().toString());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return user.getHashPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Status.ACTIVE.equals(user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Status.ACTIVE.equals(user.getStatus());
    }
}
