package io.github.khidiraliev.biotracker.dto.unit;

import jakarta.validation.constraints.NotBlank;

public record UnitCreatingRequest(
        @NotBlank String fullName,
        @NotBlank String shortName
) {
}
