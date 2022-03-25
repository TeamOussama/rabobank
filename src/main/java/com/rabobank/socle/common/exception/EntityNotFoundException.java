package com.rabobank.socle.common.exception;

import org.springframework.http.HttpStatus;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class EntityNotFoundException extends HttpException {
    private static final long serialVersionUID = -5258959358527382145L;

    public EntityNotFoundException(@Nonnull String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public EntityNotFoundException(@Nonnull Class entity, @Nullable Long entityId) {
        super(String.format("%s Not found with id %f", entity.getName(), entityId), HttpStatus.NOT_FOUND);
    }
}
