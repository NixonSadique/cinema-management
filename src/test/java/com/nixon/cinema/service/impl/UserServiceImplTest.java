package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.UserRequest;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.model.User;
import com.nixon.cinema.model.enums.Role;
import com.nixon.cinema.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_whenValidRequest_returnsSuccessMessage() {
        UserRequest request = new UserRequest(
                "john",
                "password",
                "123456789",
                "John",
                "Doe",
                "john@email.com"
        );

        when(userRepo.existsByUsername(request.username())).thenReturn(false);
        when(userRepo.existsByEmail(request.email())).thenReturn(false);
        when(passwordEncoder.encode(request.password())).thenReturn("encodedPassword");

        String result = userService.createUser(request);

        assertEquals("User saved successfully!", result);
        verify(userRepo).save(any(User.class));
    }

    @Test
    void createUser_whenUsernameExists_throwsBadRequestException() {
        UserRequest request = new UserRequest(
                "john",
                "password",
                "123456789",
                "John",
                "Doe",
                "john@email.com"
        );

        when(userRepo.existsByUsername(request.username())).thenReturn(true);

        assertThrows(BadRequestException.class, () -> userService.createUser(request));
        verify(userRepo, never()).save(any());
    }

    @Test
    void promoteUser_whenUserExists_returnsSuccessMessage() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john");

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.promoteUser(1L, Role.MANAGER);

        assertTrue(result.contains("promoted"));
        verify(userRepo).save(user);
    }


}