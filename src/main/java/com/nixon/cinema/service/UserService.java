package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.UserRequestDTO;
import com.nixon.cinema.dto.request.UserUpdateRequestDTO;
import com.nixon.cinema.dto.response.UserResponseDTO;

import java.util.List;

public interface UserService {

    String createDefaultAdmin();

    String createManager(UserRequestDTO request);

    String createUser(UserRequestDTO request);

    String updateUser(UserUpdateRequestDTO request);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO myProfile();

    UserResponseDTO getUserByUsername(String username);


}
