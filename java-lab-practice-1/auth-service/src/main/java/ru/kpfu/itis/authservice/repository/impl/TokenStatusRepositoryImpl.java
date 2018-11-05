package ru.kpfu.itis.authservice.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import ru.kpfu.itis.authservice.model.redis.TokenStatus;
import ru.kpfu.itis.authservice.repository.TokenStatusRepository;

import java.time.Duration;
import java.util.Optional;

@Repository
public class TokenStatusRepositoryImpl implements TokenStatusRepository {

    private final ReactiveValueOperations<String, TokenStatus> valueOperations;

    @Autowired
    public TokenStatusRepositoryImpl(ReactiveRedisTemplate<String, TokenStatus> reactiveRedisTemplate) {
        this.valueOperations =  reactiveRedisTemplate.opsForValue();
    }

    @Override
    public Mono<TokenStatus> findTokenStatus(final Long userId) {
        final String key = userId.toString();
        return valueOperations.get(key);
    }

    @Override
    public void saveTokenStatus(final TokenStatus tokenStatus) {
        final String key = tokenStatus.getUserId().toString();
        final Mono<Boolean> set = valueOperations.set(key, tokenStatus, Duration.ofMillis(tokenStatus.getExpirationDate().getTime()));
    }
}
