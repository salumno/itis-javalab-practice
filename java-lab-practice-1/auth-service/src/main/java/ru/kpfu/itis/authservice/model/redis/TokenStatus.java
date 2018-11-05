package ru.kpfu.itis.authservice.model.redis;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode
public class TokenStatus implements Serializable {
    private String id;
    private Long userId;
    private Date expirationDate;
    private boolean isActive;
}
