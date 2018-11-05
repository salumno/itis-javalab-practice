package ru.kpfu.itis.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.kpfu.itis.authservice.model.redis.TokenStatus;

import java.util.Optional;

public interface TokenStatusRepository {
    Mono<TokenStatus> findTokenStatus(final Long userId);
    void saveTokenStatus(final TokenStatus tokenStatus);
}
