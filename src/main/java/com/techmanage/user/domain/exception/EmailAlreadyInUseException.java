package com.techmanage.user.domain.exception;

public class EmailAlreadyInUseException extends BusinessException {
    public EmailAlreadyInUseException(String email) {
        super(String.format("Email is already in use. Email: %s", email));
    }
}
