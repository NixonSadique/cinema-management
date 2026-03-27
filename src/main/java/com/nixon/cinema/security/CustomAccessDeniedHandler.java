package com.nixon.cinema.security;

import com.nixon.cinema.exceptions.handler.StandardErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StandardErrorResponse errorResponse = new StandardErrorResponse(
                SC_FORBIDDEN,
                OffsetDateTime.now(),
                request.getServletPath(),
                accessDeniedException.getMessage(),
                List.of()
        );

        final ObjectMapper mapper = new ObjectMapper();

        response.getWriter().print(mapper.writeValueAsString(errorResponse));
    }
}
