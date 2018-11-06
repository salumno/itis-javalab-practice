package ru.kpfu.itis.authservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import ru.kpfu.itis.authservice.model.UserPersonalData;
import ru.kpfu.itis.authservice.repository.TokenStatusRepository;
import ru.kpfu.itis.authservice.model.redis.TokenStatus;
import ru.kpfu.itis.authservice.repository.UserPersonalDataRepository;
import ru.kpfu.itis.authservice.service.TokenStatusService;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class TokenStatusServiceImpl implements TokenStatusService {
    private final TokenStatusRepository tokenStatusRepository;
    private final UserPersonalDataRepository userPersonalDataRepository;

    @Autowired
    public TokenStatusServiceImpl(TokenStatusRepository tokenStatusRepository, UserPersonalDataRepository userPersonalDataRepository) {
        this.tokenStatusRepository = tokenStatusRepository;
        this.userPersonalDataRepository = userPersonalDataRepository;
    }

    @Override
    public void disableUser(final String login) {
        final UserPersonalData userPersonalData = userPersonalDataRepository.findByLogin(login).orElseThrow(
                () -> new IllegalArgumentException("User does not exist")
        );
        final Mono<TokenStatus> tokenStatus = tokenStatusRepository.findTokenStatus(userPersonalData.getId());
        tokenStatus.doOnNext(ts -> {
            if (ts == null) {
                throw new IllegalArgumentException("This user does not exist");
            }
            ts.setActive(false);
            tokenStatusRepository.saveTokenStatus(ts);
        }).subscribe();
    }

    @Override
    public void saveTokenForUser(final UserPersonalData user, final Date expDate) {
        final Mono<TokenStatus> tokenStatus = tokenStatusRepository.findTokenStatus(user.getId());
        tokenStatus
                .doOnNext(
                        ts -> {
                            if (ts == null) {
                                ts = new TokenStatus();
                                ts.setActive(true);
                                ts.setUserId(user.getId());
                                System.out.println("Token in Redis is null");
                            }
                            ts.setExpirationDate(expDate);
                            tokenStatusRepository.saveTokenStatus(ts);
                        }
                )
                .doOnError(
                        throwable -> System.out.println("Error in saveTokenForUser: " + throwable.getMessage())
                )
                .subscribe();
    }

    @Override
    public boolean isUserActive(Long userId) {
        AtomicBoolean result = new AtomicBoolean(false);
        final Mono<TokenStatus> tokenStatus = tokenStatusRepository.findTokenStatus(userId);
        tokenStatus.doOnNext(ts -> result.set(ts != null)).subscribe();
        return result.get();
    }
}
