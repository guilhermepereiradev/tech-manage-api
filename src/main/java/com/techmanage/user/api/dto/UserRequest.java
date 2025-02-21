package com.techmanage.user.api.dto;

import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.model.UserType;
import jakarta.validation.constraints.*;

import java.util.Date;

public record UserRequest(
        @NotBlank
        @Size(min = 3, max = 255)
        String name,

        @Email
        @Size(max = 100)
        @NotBlank
        String email,

        @Size(max = 25)
        @NotBlank
        @Pattern(regexp = "^\\+\\d{1,4} \\d{2,4} \\d{5}-\\d{4}$", message = "invalid phone number. Use the format: +XX XX XXXXX-XXXX")
        String phone,

        @Past
        @NotNull
        Date birthDate,

        @NotEmpty
        @Pattern(regexp = "ADMIN|EDITOR|VIEWER", message = "invalid userType. Must be ADMIN, EDITOR or VIEWER")
        String userType) {

    public User toModel() {
        return new User(name, email, phone, birthDate, UserType.valueOf(userType));
    }
}
