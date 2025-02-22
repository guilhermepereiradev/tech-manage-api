package com.techmanage.user.api.controller;

import com.techmanage.user.api.dto.UserRequest;
import com.techmanage.user.api.dto.UserResponse;
import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return new ResponseEntity<>(UserResponse.of(userService.findAllUsers()), HttpStatus.OK);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long id) {
        return new ResponseEntity<>(UserResponse.of(userService.findUserById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        var createdUser = userService.createUser(userRequest.toModel());
        return new ResponseEntity<>(UserResponse.of(createdUser), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequest userRequest) {
        var newUser = new User();
        userRequest.copyProperties(newUser);
        return new ResponseEntity<>(UserResponse.of(userService.updateUser(id, newUser)), HttpStatus.OK);
    }
}
