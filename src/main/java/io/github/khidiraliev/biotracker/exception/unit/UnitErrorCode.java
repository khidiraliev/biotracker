package io.github.khidiraliev.biotracker.exception.unit;

import io.github.khidiraliev.biotracker.exception.ErrorCode;

public enum UnitErrorCode implements ErrorCode {
    UNIT_NOT_FOUND,
    UNIT_NAME_ALREADY_EXISTS;

    @Override
    public String getCode() {
        return this.name();
    }
}
