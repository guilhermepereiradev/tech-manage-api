package com.techmanage.user.api.dto;

import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.model.UserType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;

public record UserRequest(
        @Schema(example = "John Doe")
        @NotBlank
        @Size(min = 3, max = 255)
        String name,

        @Schema(example = "johndoe@email.com")
        @Email
        @Size(max = 100)
        @NotBlank
        String email,

        @Schema(example = "+55 11 99999-9999")
        @Size(max = 25)
        @NotBlank
        @Pattern(regexp = "^\\+\\d{1,4} \\d{2,4} \\d{5}-\\d{4}$", message = "invalid phone number. Use the format: +XX XX XXXXX-XXXX")
        String phone,

        @Schema(example = "1998-06-25")
        @Past
        @NotNull
        Date birthDate,

        @Schema(example = "ADMIN")
        @NotEmpty
        @Pattern(regexp = "ADMIN|EDITOR|VIEWER", message = "invalid userType. Must be ADMIN, EDITOR or VIEWER")
        String userType) {

    public User toModel() {
        return new User(name, email, phone, birthDate, UserType.valueOf(userType));
    }

    public void copyProperties(User user) {
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setBirthDate(birthDate);
        user.setUserType(UserType.valueOf(userType));
    }
}
