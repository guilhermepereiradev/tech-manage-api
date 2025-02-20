package com.techmanage.user.domain.service;

import com.techmanage.user.domain.model.User;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
}
