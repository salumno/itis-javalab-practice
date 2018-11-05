package ru.kpfu.itis.userservice.service;

import ru.kpfu.itis.userservice.model.entity.User;
import ru.kpfu.itis.userservice.model.request.SignUpQueueRequest;

import java.util.List;

public interface UserService {
    void registerUser(final SignUpQueueRequest request);

    List<User> getAllUsers();
}
