package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.UserRequest;
import com.nixon.cinema.dto.request.UserUpdateRequest;
import com.nixon.cinema.dto.response.UserResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.User;
import com.nixon.cinema.model.enums.Role;
import com.nixon.cinema.repository.UserRepository;
import com.nixon.cinema.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public String promoteUser(Long id, Role role) {
        var user = userRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User not found with id: " + id)
        );
        user.setRole(role);
        userRepo.save(user);
        return "The user: %s, was promoted to %s.".formatted(user.getUsername(), role.name());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String createManager(UserRequest request) {
        var usernameExists = userRepo.existsByUsername(request.username());
        var emailExists = userRepo.existsByEmail(request.email());

        if (usernameExists || emailExists) {
            throw new BadRequestException("The chosen Email Already Exists");
        }

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
    public String createUser(UserRequest request) {

        var usernameExists = userRepo.existsByUsername(request.username());
        var emailExists = userRepo.existsByEmail(request.email());

        if (usernameExists || emailExists) throw new BadRequestException("The chosen Email or Username already exists");

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email().toLowerCase());
        user.setRole(Role.USER);
        userRepo.save(user);

        return "User saved successfully!";
    }

    @Override
    public String updateUser(String username, UserUpdateRequest request) {
        var user = userRepo.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Username %s not found!".formatted(username))
        );

        user.setEmail(request.email());
        user.setPhone(request.phone());

        userRepo.save(user);

        return "User updated successfully!";
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream().map(
                user -> new UserResponse(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                )
        ).toList();
    }

    @Override
    public UserResponse myProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            User user = userRepo.findByUsername(username).orElseThrow();
            return new UserResponse(
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole()
            );
        }

        throw new EntityNotFoundException("User not found!");
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        User user = userRepo.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("User not found")
        );
        return new UserResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}
