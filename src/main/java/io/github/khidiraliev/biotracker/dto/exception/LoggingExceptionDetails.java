package io.github.khidiraliev.biotracker.dto.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public record LoggingExceptionDetails(
        String uri,
        Integer status,
        String method,
        ExceptionResponse response
) {
    public static LoggingExceptionDetails of(ExceptionResponse response, HttpStatus status, HttpServletRequest request) {
        return new LoggingExceptionDetails(
                request.getRequestURI(),
                status.value(),
                request.getMethod(),
                response
        );
    }
}
