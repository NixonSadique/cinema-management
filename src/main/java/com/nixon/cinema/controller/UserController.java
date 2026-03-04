package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.UserRequestDTO;
import com.nixon.cinema.dto.request.UserUpdateRequestDTO;
import com.nixon.cinema.dto.response.UserResponseDTO;
import com.nixon.cinema.service.UserService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cinema/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/default")
    ResponseEntity<String> defaultUser() {
        return  new ResponseEntity<>(userService.createDefaultAdmin(), HttpStatus.CREATED);
    }

    @PostMapping("/create/manager")
    ResponseEntity<String> createManager(@RequestBody UserRequestDTO request) {
        return new  ResponseEntity<>(userService.createManager(request), HttpStatus.CREATED);
    }

    @PostMapping("/create/user")
    ResponseEntity<String> createUser(@RequestBody UserRequestDTO request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    ResponseEntity<String> updateUser(@RequestBody UserUpdateRequestDTO request) {
        return new ResponseEntity<>(userService.updateUser(request), HttpStatus.OK);
    }

    @GetMapping("/get")
    ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/get/{username}")
    ResponseEntity<UserResponseDTO> getUser(@PathVariable @Size(min =6, max = 20) String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/me")
    ResponseEntity<UserResponseDTO> getMyProfile() {
        return ResponseEntity.ok(userService.myProfile());
    }

}
