package com.techmanage.user.domain.exception;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User not found with id %d", id));
    }
}
