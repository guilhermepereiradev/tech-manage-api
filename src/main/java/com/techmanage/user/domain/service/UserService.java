package com.techmanage.user.domain.service;

import com.techmanage.user.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    User findUserById(Long id);
    User createUser(User user);
}
