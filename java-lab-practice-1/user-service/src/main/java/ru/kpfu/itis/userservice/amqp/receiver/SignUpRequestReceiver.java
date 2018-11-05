package ru.kpfu.itis.userservice.amqp.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.userservice.config.RabbitConfig;
import ru.kpfu.itis.userservice.model.request.SignUpQueueRequest;
import ru.kpfu.itis.userservice.service.UserService;

import java.io.IOException;

@Component
public class SignUpRequestReceiver {
    private final ObjectMapper objectMapper;
    private final UserService userService;

    @Autowired
    public SignUpRequestReceiver(final ObjectMapper objectMapper, UserService userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_SIGN_UP_REQUEST)
    public void signUpRequestProcess(final byte[] messageAsBytes) throws IOException {
        String jsonMessage = new String(messageAsBytes);
        SignUpQueueRequest signUpRequest = objectMapper.readValue(jsonMessage, SignUpQueueRequest.class);
        userService.registerUser(signUpRequest);
    }
}
