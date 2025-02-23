package com.techmanage.user.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;
import java.util.List;

public record UserResponse(
        @Schema(example = "1")
        Long id,

        @Schema(example = "John Doe")
        String fullName,

        @Schema(example = "johndoe@email.com")
        String email,

        @Schema(example = "+55 11 99999-9999")
        String phone,

        @Schema(example = "1998-06-25")
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,

        @Schema(example = "ADMIN")
        UserType userType) {

    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getFullName(), user.getEmail(), user.getPhone(), user.getBirthDate(), user.getUserType());
    }

    public static List<UserResponse> of(List<User> users) {
        return users.stream().map(UserResponse::of).toList();
    }
}
