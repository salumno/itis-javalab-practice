package ru.kpfu.itis.userservice.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_SIGN_UP_REQUEST = "sign-up-request-queue";
    public static final String QUEUE_SIGN_UP_RESPONSE = "sign-up-response-queue";
}
