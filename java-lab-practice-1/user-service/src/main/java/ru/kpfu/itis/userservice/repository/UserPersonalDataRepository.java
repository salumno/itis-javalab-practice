package ru.kpfu.itis.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kpfu.itis.userservice.model.entity.UserPersonalData;

public interface UserPersonalDataRepository extends JpaRepository<UserPersonalData, Long> {

}
