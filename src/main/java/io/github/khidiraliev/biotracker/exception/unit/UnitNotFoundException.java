package io.github.khidiraliev.biotracker.exception.unit;

import io.github.khidiraliev.biotracker.exception.BusinessException;
import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnitNotFoundException extends BusinessException {
    public UnitNotFoundException(Long unitId) {
        super(
                "Unit with id " + unitId + " not found.",
                UnitErrorCode.UNIT_NOT_FOUND,
                HttpStatus.NOT_FOUND,
                Map.of("id", unitId)
        );
    }
}
