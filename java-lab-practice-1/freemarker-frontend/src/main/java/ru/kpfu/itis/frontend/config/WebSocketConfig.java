package ru.kpfu.itis.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import ru.kpfu.itis.frontend.websocket.UserUpdatesHandler;

import java.util.Map;

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(userUpdatesHandler(), "/userUpdates/{login}").addInterceptors(userUpdatesInterceptor());
    }

    @Bean
    public HandshakeInterceptor userUpdatesInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) {
                String path = serverHttpRequest.getURI().getPath();
                String login = path.substring(path.lastIndexOf('/') + 1);
                map.put("login", login);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {
            }
        };
    }

    @Bean
    public UserUpdatesHandler userUpdatesHandler() {
        return new UserUpdatesHandler();
    }
}
