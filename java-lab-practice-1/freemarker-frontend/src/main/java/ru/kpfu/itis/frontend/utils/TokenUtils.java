package ru.kpfu.itis.frontend.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TokenUtils {

    private static String token;

    public HttpHeaders getAuthorizationHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, token);
        return headers;
    }

    public static void setToken(final String token) {
        TokenUtils.token = token;
    }
}
