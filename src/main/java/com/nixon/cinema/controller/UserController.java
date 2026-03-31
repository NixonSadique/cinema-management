package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.UserRequest;
import com.nixon.cinema.dto.request.UserUpdateRequest;
import com.nixon.cinema.dto.response.UserResponse;
import com.nixon.cinema.model.enums.Role;
import com.nixon.cinema.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cinema/v1")
@RequiredArgsConstructor
@Tag(name = "2.User Controller", description = "Endpoints for managing users, including creation, retrieval, update, and deletion")
public class UserController {

    private final UserService userService;

    @PutMapping("/users/{id}/promote")
    ResponseEntity<String> createAdmin(@PathVariable @Min(1L) Long id, @RequestParam @NotBlank Role role) {
        return new ResponseEntity<>(userService.promoteUser(id, role), HttpStatus.CREATED);
    }

    @PostMapping("/users/manager")
    ResponseEntity<String> createManager(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(userService.createManager(request), HttpStatus.CREATED);
    }

    @PostMapping("/users")
    ResponseEntity<String> createUser(@RequestBody @Valid UserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/users")
    ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{username}")
    ResponseEntity<String> updateUser(@RequestBody @Valid UserUpdateRequest request, @NotBlank String username) {
        return new ResponseEntity<>(userService.updateUser(username, request), HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    ResponseEntity<UserResponse> getUser(@PathVariable @Size(min = 6, max = 20) String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/users/me")
    ResponseEntity<UserResponse> getMyProfile() {
        return ResponseEntity.ok(userService.myProfile());
    }

}
