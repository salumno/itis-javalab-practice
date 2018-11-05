package ru.kpfu.itis.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.userservice.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
