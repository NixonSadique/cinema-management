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
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email().toLowerCase());
        user.setRole(Role.MANAGER);
        userRepo.save(user);
        return "New Manager Created!";
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
        return userRepo.findAll().stream().map(
                user -> new UserResponseDTO(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                )
        ).toList();
    }

    @Override
    public UserResponseDTO myProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepo.findByUsername(username).orElseThrow();
            return new UserResponseDTO(
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole()
            );
        }

        throw new AuthenticationCredentialsNotFoundException("User not found");
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        return new UserResponseDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
