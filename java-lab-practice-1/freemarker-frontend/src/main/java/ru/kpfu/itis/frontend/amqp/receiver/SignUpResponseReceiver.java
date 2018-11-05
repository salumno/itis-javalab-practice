package ru.kpfu.itis.frontend.amqp.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.frontend.config.RabbitConfig;
import ru.kpfu.itis.frontend.model.SignUpCheersParameters;
import ru.kpfu.itis.frontend.websocket.UserUpdatesHandler;

import java.io.IOException;

@Component
public class SignUpResponseReceiver {
    private final ObjectMapper objectMapper;
    private final UserUpdatesHandler userUpdatesHandler;

    @Autowired
    public SignUpResponseReceiver(final ObjectMapper objectMapper,
                                  final UserUpdatesHandler userUpdatesHandler) {
        this.objectMapper = objectMapper;
        this.userUpdatesHandler = userUpdatesHandler;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_SIGN_UP_RESPONSE)
    public void signUpResponseProcess(final byte[] messageAsBytes) throws IOException {
        String jsonMessage = new String(messageAsBytes);
        SignUpCheersParameters singUpCheersParameters = objectMapper.readValue(jsonMessage, SignUpCheersParameters.class);
        userUpdatesHandler.sendSighUpCheers(singUpCheersParameters);
    }
}
