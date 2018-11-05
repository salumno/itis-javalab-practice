package ru.kpfu.itis.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import ru.kpfu.itis.authservice.model.redis.TokenStatus;

@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveRedisTemplate<String, TokenStatus> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {

        StringRedisSerializer keySerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer<TokenStatus> valueSerializer = new Jackson2JsonRedisSerializer<>(TokenStatus.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String, TokenStatus> builder = RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<String, TokenStatus> context = builder.value(valueSerializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
