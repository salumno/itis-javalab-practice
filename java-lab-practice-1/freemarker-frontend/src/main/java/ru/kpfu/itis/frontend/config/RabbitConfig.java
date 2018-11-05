package ru.kpfu.itis.frontend.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_SIGN_UP_REQUEST = "sign-up-request-queue";
    public static final String QUEUE_SIGN_UP_RESPONSE = "sign-up-response-queue";
//    public static final String EXCHANGE_SIGN_UP = "sign-up-exchange";

    /*@Bean
    Queue signUpRequestQueue() {
        return QueueBuilder.durable(QUEUE_SIGN_UP_REQUEST).build();
    }

    @Bean
    Exchange signUpExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_SIGN_UP).build();
    }

    @Bean
    Binding binding(Queue signUpRequestQueue, Exchange signUpExchange) {
        return BindingBuilder.bind(signUpRequestQueue).to(signUpExchange).with(QUEUE_SIGN_UP_REQUEST).noargs();
    }*/

}
