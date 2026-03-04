package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.UserRequestDTO;
import com.nixon.cinema.dto.request.UserUpdateRequestDTO;
import com.nixon.cinema.dto.response.UserResponseDTO;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.User;
import com.nixon.cinema.model.enums.Role;
import com.nixon.cinema.repository.UserRepository;
import com.nixon.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String createDefaultAdmin() {
        User user = new User();
        user.setEmail("admin@email.com");
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setPhone("123456789");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setRole(Role.ADMIN);
        userRepo.save(user);
        return "Default Admin Created!";
    }

    @Override
    public String createManager(UserRequestDTO request) {
        return "";
    }

    @Override
    public String createUser(UserRequestDTO request) {
        return "";
    }

    @Override
    public String updateUser(UserUpdateRequestDTO request) {
        return "";
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponseDTO myProfile() {
        throw new EntityNotFoundException("User not found");
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        return null;
    }
}
