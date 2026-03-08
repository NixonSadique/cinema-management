package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.AuthenticationRequestDTO;
import com.nixon.cinema.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse login(AuthenticationRequestDTO request);
}
