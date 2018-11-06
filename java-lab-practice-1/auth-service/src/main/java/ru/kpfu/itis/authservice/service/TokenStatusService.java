package ru.kpfu.itis.authservice.service;

import ru.kpfu.itis.authservice.model.User;
import ru.kpfu.itis.authservice.model.UserPersonalData;

import java.util.Date;

public interface TokenStatusService {
    void disableUser(final String userLogin);

    void saveTokenForUser(final UserPersonalData user, final Date expDate);

    boolean isUserActive(final Long userId);
}
