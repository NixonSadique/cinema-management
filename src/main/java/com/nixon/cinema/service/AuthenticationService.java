package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.AuthenticationRequest;
import com.nixon.cinema.dto.response.TokenResponse;

public interface AuthenticationService {

    TokenResponse login(AuthenticationRequest request);
}
