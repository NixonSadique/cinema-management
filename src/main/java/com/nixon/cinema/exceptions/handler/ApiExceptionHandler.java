package com.nixon.cinema.exceptions.handler;

import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        StandardErrorResponse response = new StandardErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                OffsetDateTime.now(),
                request.getServletPath(),
                ex.getMessage(),
                List.of()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<StandardErrorResponse> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        StandardErrorResponse response = new StandardErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now(),
                request.getServletPath(),
                ex.getMessage(),
                List.of()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardErrorResponse> handleException(Exception ex, HttpServletRequest request) {
        StandardErrorResponse response = new StandardErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                OffsetDateTime.now(),
                request.getServletPath(),
                ex.getMessage(),
                List.of()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        StandardErrorResponse response = new StandardErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                OffsetDateTime.now(),
                request.getServletPath(),
                ex.getMessage(),
                ex.getBindingResult().getFieldErrors().stream().map(
                        fieldError ->
                                new ValidationError(fieldError.getField(), fieldError.getDefaultMessage())
                ).toList()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
