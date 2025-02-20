package com.techmanage.user.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.model.UserType;

import java.util.Date;
import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        String phone,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,
        UserType userType) {

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getPhone(), user.getBirthDate(), user.getUserType());
    }

    public static List<UserResponse> of(List<User> users) {
        return users.stream().map(UserResponse::of).toList();
    }
}
