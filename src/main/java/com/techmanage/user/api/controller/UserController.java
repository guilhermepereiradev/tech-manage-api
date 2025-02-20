package com.techmanage.user.api.controller;

import com.techmanage.user.api.dto.UserResponse;
import com.techmanage.user.domain.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping
    public ResponseEntity<List<UserResponse>> findAllUser() {
        return new ResponseEntity<>(UserResponse.of(userService.findAllUsers()), HttpStatus.OK);
    }

}
