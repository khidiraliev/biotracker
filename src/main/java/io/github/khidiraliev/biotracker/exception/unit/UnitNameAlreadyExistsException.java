package io.github.khidiraliev.biotracker.exception.unit;

import io.github.khidiraliev.biotracker.exception.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnitNameAlreadyExistsException extends BusinessException {
    public UnitNameAlreadyExistsException(String name) {
        super(
                "Unit with full name " + name + " already exists",
                UnitErrorCode.UNIT_NAME_ALREADY_EXISTS,
                HttpStatus.CONFLICT,
                Map.of("name", name)
        );
    }
}
