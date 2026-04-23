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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String promoteUser(Long id, Role role) {
        log.info("Attempting to promote user with id: {} to role: {}", id, role);
        var user = userRepo.findById(id).orElseThrow(
                () -> {
                    log.error("User with id: {} not found", id);
                    return new EntityNotFoundException("User not found with id: " + id);
                }
        );
        user.setRole(role);
        userRepo.save(user);

        log.info("User with id: {} promoted to {}", id, role);

        return "The user: %s, was promoted to %s.".formatted(user.getUsername(), role.name());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String createManager(UserRequest request) {
        log.info("Creating manager with username: {}", request.username());

        var usernameExists = userRepo.existsByUsername(request.username());
        var emailExists = userRepo.existsByEmail(request.email());

        if (usernameExists || emailExists) {
            log.warn("Failed to create manager. Username or email already exists: {}", request.username());
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

        log.info("Manager created successfully: {}", user.getUsername());

        return "New Manager Created!";
    }

    @Override
    public String createUser(UserRequest request) {
        log.info("Creating user with username: {}", request.username());

        var usernameExists = userRepo.existsByUsername(request.username());
        var emailExists = userRepo.existsByEmail(request.email());

        if (usernameExists || emailExists) {
            log.warn("Failed to create user. Username or email already exists: {}", request.username());
            throw new BadRequestException("The chosen Email or Username already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setPhone(request.phone());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email().toLowerCase());
        user.setRole(Role.USER);
        userRepo.save(user);

        log.info("User created successfully: {}", user.getUsername());

        return "User saved successfully!";
    }

    @Override
    public String updateUser(String username, UserUpdateRequest request) {
        log.info("Updating user: {}", username);

        var user = userRepo.findByUsername(username).orElseThrow(
                () -> {
                    log.error("User not found for update: {}", username);
                    return new EntityNotFoundException("Username %s not found!".formatted(username));
                }
        );

        user.setEmail(request.email());
        user.setPhone(request.phone());

        userRepo.save(user);

        log.info("User updated successfully: {}", username);

        return "User updated successfully!";
    }

    @Override
    public List<UserResponse> getAllUsers() {
        log.debug("Fetching all users");

        List<UserResponse> users = userRepo.findAll().stream().map(
                user -> new UserResponse(
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole()
                )
        ).toList();

        log.debug("Total users fetched: {}", users.size());

        return users;
    }

    @Override
    public UserResponse myProfile() {
        log.debug("Fetching profile of currently authenticated user");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            User user = userRepo.findByUsername(username).orElseThrow(() -> {
                log.error("Authenticated user not found in DB: {}", username);
                return new EntityNotFoundException("User not found!");
            });

            log.debug("Profile fetched successfully for user: {}", username);

            return new UserResponse(
                    user.getUsername(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getRole()
            );
        }

        log.error("No authenticated user found in security context");
        throw new EntityNotFoundException("User not found!");
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        log.debug("Fetching user by username: {}", username);

        User user = userRepo.findByUsername(username).orElseThrow(
                () -> {
                    log.error("User not found: {}", username);
                    return new EntityNotFoundException("User not found");
                }
        );

        log.debug("User fetched successfully: {}", username);

        return new UserResponse(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }
}