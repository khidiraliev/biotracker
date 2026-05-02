package io.github.khidiraliev.biotracker.controller;

import io.github.khidiraliev.biotracker.dto.exception.ExceptionResponse;
import io.github.khidiraliev.biotracker.dto.exception.LoggingExceptionDetails;
import io.github.khidiraliev.biotracker.dto.exception.ParameterValidationErrorDetail;
import io.github.khidiraliev.biotracker.dto.exception.RequestBodyValidationErrorDetail;
import io.github.khidiraliev.biotracker.exception.BusinessException;
import io.github.khidiraliev.biotracker.exception.StandardErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //TODO HttpRequestMethodNotSupportedException
    //TODO MethodArgumentTypeMismatchException

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(HandlerMethodValidationException ex,
                                                                       HttpServletRequest request) {
        List<ParameterValidationErrorDetail> parameterDetails = ex.getValueResults().stream()
                .map(result -> new ParameterValidationErrorDetail(
                        result.getMethodParameter().getParameterName(),
                        result.getResolvableErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList(),
                        result.getArgument()
                ))
                .toList();

        List<RequestBodyValidationErrorDetail> requestBodyDetails = ex.getBeanResults().stream()
                .map(result -> new RequestBodyValidationErrorDetail(
                        result.getMethodParameter().getParameterName(),
                        result.getFieldErrors().stream()
                                .map(error -> Map.of(
                                        "field", error.getField(),
                                        "message", error.getDefaultMessage(),
                                        "rejectedValue", error.getRejectedValue()
                                        )
                                ).toList()
                )).toList();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse response = new ExceptionResponse(
                Instant.now(),
                StandardErrorCode.VALIDATION_ERROR.getCode(),
                "Validation failed.",
                Map.of(
                        "parameters", parameterDetails,
                        "content", requestBodyDetails
                )
        );

        LoggingExceptionDetails loggingDetails = LoggingExceptionDetails.of(response, status, request);
        log.info("Handler method validation exception - details: {}", loggingDetails);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                            HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ExceptionResponse response = new ExceptionResponse(
                Instant.now(),
                StandardErrorCode.RESOURCE_NOT_FOUND.getCode(),
                "Resource not found.",
                Map.of("uri", request.getRequestURI())
        );

        LoggingExceptionDetails loggingDetails = LoggingExceptionDetails.of(response, status, request);
        log.info("No resource found exception - details: {}", loggingDetails);
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        HttpStatus status = ex.getStatus();
        ExceptionResponse response = new ExceptionResponse(
                Instant.now(),
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getDetails()
        );

        LoggingExceptionDetails loggingDetails = LoggingExceptionDetails.of(response, status, request);
        log.info("Business exception - details: {}", loggingDetails);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleInternalError(Exception ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ExceptionResponse response = new ExceptionResponse(
                Instant.now(),
                StandardErrorCode.INTERNAL_ERROR.getCode(),
                "Internal error.",
                Map.of()
        );

        LoggingExceptionDetails loggingDetails = LoggingExceptionDetails.of(response, status, request);
        log.error("Internal error - error details: {}", loggingDetails, ex);

        return new ResponseEntity<>(response, status);
    }
}
