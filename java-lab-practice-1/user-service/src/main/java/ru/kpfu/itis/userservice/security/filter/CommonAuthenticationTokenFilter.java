package ru.kpfu.itis.userservice.security.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.kpfu.itis.userservice.security.model.AuthUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.singletonList;

public class CommonAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authToken = request.getHeader(tokenHeader);

        if (!StringUtils.isEmpty(authToken)) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", authToken);
            headers.setAccept(singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(authToken, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    "http://AUTH-SERVICE/auth/verify", HttpMethod.POST, entity, String.class
            );

            UserDetails userDetails = prepareUserDetails(responseEntity.getBody());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private UserDetails prepareUserDetails(String jsonUserDetails) throws IOException{

        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode root = objectMapper.readTree(jsonUserDetails);

        final Long userId = root.get("userId").asLong();
        final String login = root.get("login").asText();
        final String status =  root.get("status").asText();
        final String role =  root.get("role").asText();

        return new AuthUser(userId, login, status, role);
    }
}
