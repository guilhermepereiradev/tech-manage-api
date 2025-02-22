package com.techmanage.user.domain.service.impl;

import com.techmanage.user.domain.exception.EmailAlreadyInUseException;
import com.techmanage.user.domain.exception.UserNotFoundException;
import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.repository.UserRepository;
import com.techmanage.user.domain.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    @Transactional
    public User createUser(User user) {
        validateUser(user, true);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        var user = findUserById(id);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, User newUser) {
        var user = findUserById(id);
        newUser.setId(user.getId());

        boolean emailIsNew = !newUser.getEmail().equals(user.getEmail());
        validateUser(newUser, emailIsNew);

        return userRepository.save(newUser);
    }

    private void validateUser(User user, boolean emailIsNew) {
        if(emailIsNew && userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyInUseException(user.getEmail());
        }
    }
}
