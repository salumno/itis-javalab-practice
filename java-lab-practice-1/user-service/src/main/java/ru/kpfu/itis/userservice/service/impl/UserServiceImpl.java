package ru.kpfu.itis.userservice.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kpfu.itis.userservice.amqp.sender.SignUpResponseSender;
import ru.kpfu.itis.userservice.config.ServiceApiConfig;
import ru.kpfu.itis.userservice.model.dto.CatDto;
import ru.kpfu.itis.userservice.model.dto.SignUpCheersParameters;
import ru.kpfu.itis.userservice.model.entity.User;
import ru.kpfu.itis.userservice.model.entity.UserPersonalData;
import ru.kpfu.itis.userservice.model.request.SignUpQueueRequest;
import ru.kpfu.itis.userservice.repository.UserPersonalDataRepository;
import ru.kpfu.itis.userservice.repository.UserRepository;
import ru.kpfu.itis.userservice.security.model.Role;
import ru.kpfu.itis.userservice.security.model.Status;
import ru.kpfu.itis.userservice.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserPersonalDataRepository userPersonalDataRepository;
    private final SignUpResponseSender signUpResponseSender;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;

    public UserServiceImpl(final UserRepository userRepository,
                           final UserPersonalDataRepository userPersonalDataRepository,
                           final SignUpResponseSender signUpResponseSender,
                           final PasswordEncoder passwordEncoder,
                           final RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.userPersonalDataRepository = userPersonalDataRepository;
        this.signUpResponseSender = signUpResponseSender;
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    @Override
    public void registerUser(final SignUpQueueRequest request) {
        String photoUrl = getPhotoUrl();

        final User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .login(request.getLogin())
                .photoUrl(photoUrl)
                .build();

        final UserPersonalData userPersonalData = UserPersonalData.builder()
                .login(request.getLogin())
                .hashPassword(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .status(Status.ACTIVE)
                .user(user)
                .build();
        userRepository.save(user);
        userPersonalDataRepository.save(userPersonalData);

        sendCheers(userPersonalData.getLogin(), user.getPhotoUrl());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    private void sendCheers(final String login, final String photoUrl) {
        final SignUpCheersParameters cheers = new SignUpCheersParameters();
        cheers.setLogin(login);
        cheers.setPhotoUrl(photoUrl);
        signUpResponseSender.sendSignUpResponse(cheers);
    }

    private String getPhotoUrl() {
        CatDto cat = restTemplate.getForEntity(ServiceApiConfig.CAT_SERVICE_API_BASE_URL + "/cats/search", CatDto.class).getBody();
        return cat.getUrl();
    }

}
