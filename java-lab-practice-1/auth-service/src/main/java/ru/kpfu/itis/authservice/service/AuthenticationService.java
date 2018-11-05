package ru.kpfu.itis.authservice.service;

import ru.kpfu.itis.authservice.model.AuthToken;
import ru.kpfu.itis.authservice.model.UserAuthInfo;
import ru.kpfu.itis.authservice.model.UserCredentials;

public interface AuthenticationService {
    AuthToken signIn(final UserCredentials credentials);

    UserAuthInfo verify(final String authToken);
}
