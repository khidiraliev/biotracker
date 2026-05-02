package io.github.khidiraliev.biotracker.dto.exception;

import java.util.List;
import java.util.Map;

public record RequestBodyValidationErrorDetail(
        String name,
        List<Map<String, Object>> fields
) {

}
