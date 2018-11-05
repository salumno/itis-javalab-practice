package ru.kpfu.itis.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.authservice.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
}
