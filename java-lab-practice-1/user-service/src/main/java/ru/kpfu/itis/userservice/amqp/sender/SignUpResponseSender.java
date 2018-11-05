package ru.kpfu.itis.userservice.amqp.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.userservice.config.RabbitConfig;
import ru.kpfu.itis.userservice.model.dto.SignUpCheersParameters;

@Service
public class SignUpResponseSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SignUpResponseSender(final RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendSignUpResponse(final SignUpCheersParameters cheersParameters) {
        try {
            String requestJson = objectMapper.writeValueAsString(cheersParameters);
            Message message = MessageBuilder
                    .withBody(requestJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_SIGN_UP_RESPONSE, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
