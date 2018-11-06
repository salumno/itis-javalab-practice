package ru.kpfu.itis.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.authservice.model.UserPersonalData;
import ru.kpfu.itis.authservice.repository.UserPersonalDataRepository;
import ru.kpfu.itis.authservice.security.model.Status;
import ru.kpfu.itis.authservice.service.UserServeService;

@Service
public class UserServeServiceImpl implements UserServeService {
    private final UserPersonalDataRepository userPersonalDataRepository;

    @Autowired
    public UserServeServiceImpl(UserPersonalDataRepository userPersonalDataRepository) {
        this.userPersonalDataRepository = userPersonalDataRepository;
    }

    @Override
    public void disableUser(String userLogin) {
        UserPersonalData user = userPersonalDataRepository.findByLogin(userLogin).orElseThrow(
                () -> new IllegalArgumentException("User with login " + userLogin + " does not exist"));
        user.setStatus(Status.BANNED);
        userPersonalDataRepository.save(user);
    }
}
