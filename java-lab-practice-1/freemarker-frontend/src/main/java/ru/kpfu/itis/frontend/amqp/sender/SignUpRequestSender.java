package ru.kpfu.itis.frontend.amqp.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.frontend.config.RabbitConfig;
import ru.kpfu.itis.frontend.model.SignUpParameters;

@Service
public class SignUpRequestSender {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public SignUpRequestSender(final RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendSignUpRequest(final SignUpParameters parameters) {
        try {
            String requestJson = objectMapper.writeValueAsString(parameters);
            Message message = MessageBuilder
                    .withBody(requestJson.getBytes())
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();
            this.rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_SIGN_UP_REQUEST, message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
