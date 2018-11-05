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
    public void disableUser(Long userId) {
        UserPersonalData user = userPersonalDataRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("User with id " + userId + " does not exist"));
        user.setStatus(Status.BANNED);
        userPersonalDataRepository.save(user);
    }
}
