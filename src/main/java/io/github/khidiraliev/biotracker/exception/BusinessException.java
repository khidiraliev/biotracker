package io.github.khidiraliev.biotracker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BusinessException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;
    private final Map<String, Object> details;

    public BusinessException(String message, String errorCode, HttpStatus status, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.details = details;
    }

    public BusinessException(String message, ErrorCode errorCode, HttpStatus status, Map<String, Object> details) {
        super(message);
        this.errorCode = errorCode.getCode();
        this.status = status;
        this.details = details;
    }
}
