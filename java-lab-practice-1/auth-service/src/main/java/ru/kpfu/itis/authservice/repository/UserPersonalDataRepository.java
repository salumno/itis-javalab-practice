package ru.kpfu.itis.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.authservice.model.UserPersonalData;

import java.util.Optional;

public interface UserPersonalDataRepository extends JpaRepository<UserPersonalData, Long> {
    Optional<UserPersonalData> findByLogin(final String login);
}
