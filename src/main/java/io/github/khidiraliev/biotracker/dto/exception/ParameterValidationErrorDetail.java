package io.github.khidiraliev.biotracker.dto.exception;

import java.util.List;

public record ParameterValidationErrorDetail(
        String parameter,
        List<String> messages,
        Object rejectedValue
) {

}
