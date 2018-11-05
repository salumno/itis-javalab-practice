package ru.kpfu.itis.frontend.websocket;

import lombok.SneakyThrows;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.kpfu.itis.frontend.model.SignUpCheersParameters;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserUpdatesHandler extends TextWebSocketHandler {

    private Map<String, WebSocketSession> sessions;

    public UserUpdatesHandler() {
        sessions = new ConcurrentHashMap<>();
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        String login = session.getAttributes().get("login").toString();
        sessions.put(login, session);
    }

    @Override
    protected void handleTextMessage(final WebSocketSession session, final TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }

    @SneakyThrows
    public void sendSighUpCheers(final SignUpCheersParameters parameters) {
        sessions.get(parameters.getLogin()).sendMessage(new TextMessage(parameters.getPhotoUrl()));
    }
}
