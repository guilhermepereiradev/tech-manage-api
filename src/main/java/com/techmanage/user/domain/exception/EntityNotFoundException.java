package com.techmanage.user.domain.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
