package com.nixon.cinema.security;

import com.nixon.cinema.exceptions.handler.StandardErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StandardErrorResponse errorResponse = new StandardErrorResponse(
                SC_UNAUTHORIZED,
                OffsetDateTime.now(),
                request.getServletPath(),
                authException.getMessage(),
                List.of()
        );


        final ObjectMapper mapper = new ObjectMapper();

        response.getWriter().print(mapper.writeValueAsString(errorResponse));
    }
}
