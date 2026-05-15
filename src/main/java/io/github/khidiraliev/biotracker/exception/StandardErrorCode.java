package io.github.khidiraliev.biotracker.exception;

public enum StandardErrorCode implements ErrorCode {
    INTERNAL_ERROR,
    RESOURCE_NOT_FOUND,
    VALIDATION_ERROR;

    @Override
    public String getCode() {
        return this.name();
    }
}
