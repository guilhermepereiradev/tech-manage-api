package com.techmanage.user.domain.service;

import com.techmanage.user.domain.exception.EmailAlreadyInUseException;
import com.techmanage.user.domain.exception.UserNotFoundException;
import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.model.UserType;
import com.techmanage.user.domain.repository.UserRepository;
import com.techmanage.user.domain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;
    
    private final String MSG_EMAIL_ALREADY_IN_USE = "Email is already in use. Email: %s";
    private final String MSG_USER_NOT_FOUND = "User not found with id %d";
    
    private final Long ID_USER_1 = 1L;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = new User(ID_USER_1, "John Doe", "johndoe@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        user2 = new User(2L, "Jane Doe", "janedoe@email.com", "+55 11 99999-9998", new Date(), UserType.EDITOR);
    }

    @Test
    void findAllUsers_ShouldFindAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<User> users = userService.findAllUsers();

        assertThat(users).containsExactly(user1, user2);
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findUserById_ShouldFindUserById() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.of(user1));

        User user = userService.findUserById(ID_USER_1);

        assertThat(user).isEqualTo(user1);
        verify(userRepository).findById(ID_USER_1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void findUserById_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUserById(ID_USER_1));

        assertThat(exception.getMessage()).isEqualTo(String.format(MSG_USER_NOT_FOUND, ID_USER_1));
        verify(userRepository).findById(ID_USER_1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUser_ShouldCreateUser() {
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(false);
        when(userRepository.save(user1)).thenReturn(user1);

        User user = userService.createUser(user1);

        assertThat(user).isEqualTo(user1);
        verify(userRepository).existsByEmail(user1.getEmail());
        verify(userRepository).save(user1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void createUser_ShouldNotCreateUser_WhenEmailAlreadyInUse() {
        when(userRepository.existsByEmail(user1.getEmail())).thenReturn(true);

        EmailAlreadyInUseException exception = assertThrows(EmailAlreadyInUseException.class, () -> userService.createUser(user1));

        assertThat(exception.getMessage()).isEqualTo(String.format(MSG_EMAIL_ALREADY_IN_USE, user1.getEmail()));
        verify(userRepository).existsByEmail(user1.getEmail());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.of(user1));
        doNothing().when(userRepository).delete(user1);

        userService.deleteUser(ID_USER_1);

        verify(userRepository).findById(ID_USER_1);
        verify(userRepository).delete(user1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void deleteUser_ShouldThrownUserNotFoundException_WhenUserNotFound() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.deleteUser(ID_USER_1));

        assertThat(exception.getMessage()).isEqualTo(String.format(MSG_USER_NOT_FOUND, user1.getId()));
        verify(userRepository).findById(ID_USER_1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser_ShouldUpdateUser() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.of(user1));
        when(userRepository.existsByEmail(user2.getEmail())).thenReturn(false);
        when(userRepository.save(user2)).thenReturn(user2);

        User user = userService.updateUser(ID_USER_1, user2);

        assertThat(user).isEqualTo(user2);
        assertThat(user.getId()).isEqualTo(ID_USER_1);
        verify(userRepository).findById(ID_USER_1);
        verify(userRepository).existsByEmail(user2.getEmail());
        verify(userRepository).save(user2);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser_ShouldThrowUserNotFoundException_WhenUserNotFound() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.updateUser(ID_USER_1, user2));

        assertThat(exception.getMessage()).isEqualTo(String.format(MSG_USER_NOT_FOUND, ID_USER_1));
        verify(userRepository).findById(ID_USER_1);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void updateUser_ShouldThrowEmailAlreadyInUse_WhenEmailAlreadyInUse() {
        when(userRepository.findById(ID_USER_1)).thenReturn(Optional.of(user1));
        when(userRepository.existsByEmail(user2.getEmail())).thenReturn(true);

        EmailAlreadyInUseException exception = assertThrows(EmailAlreadyInUseException.class, () -> userService.updateUser(ID_USER_1, user2));

        assertThat(exception.getMessage()).isEqualTo(String.format(MSG_EMAIL_ALREADY_IN_USE, user2.getEmail()));
        verify(userRepository).findById(ID_USER_1);
        verify(userRepository).existsByEmail(user2.getEmail());
        verifyNoMoreInteractions(userRepository);
    }
}
