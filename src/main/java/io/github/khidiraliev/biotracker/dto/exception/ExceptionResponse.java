package io.github.khidiraliev.biotracker.dto.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Map;

public record ExceptionResponse(
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Instant timestamp,
        String error,
        String message,
        Map<String, Object> details
) {

}
