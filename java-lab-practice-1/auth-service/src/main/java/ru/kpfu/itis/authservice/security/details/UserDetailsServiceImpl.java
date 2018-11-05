package ru.kpfu.itis.authservice.security.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.authservice.model.User;
import ru.kpfu.itis.authservice.model.UserPersonalData;
import ru.kpfu.itis.authservice.repository.UserPersonalDataRepository;
import ru.kpfu.itis.authservice.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserPersonalDataRepository userPersonalDataRepository;

    @Autowired
    public UserDetailsServiceImpl(UserPersonalDataRepository userPersonalDataRepository) {
        this.userPersonalDataRepository = userPersonalDataRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserPersonalData user = userPersonalDataRepository.findByLogin(username).orElseThrow(
                () -> new IllegalArgumentException("User with name " + username +  " is not found ")
        );
        return new UserDetailsImpl(user);
    }
}
