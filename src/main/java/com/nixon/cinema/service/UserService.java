package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.UserRequest;
import com.nixon.cinema.dto.request.UserUpdateRequest;
import com.nixon.cinema.dto.response.UserResponse;
import com.nixon.cinema.model.enums.Role;

import java.util.List;

public interface UserService {

    String promoteUser(Long id, Role role);

    String createManager(UserRequest request);

    String createUser(UserRequest request);

    String updateUser(String username, UserUpdateRequest request);

    List<UserResponse> getAllUsers();

    UserResponse myProfile();

    UserResponse getUserByUsername(String username);


}
