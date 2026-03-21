package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.MovieCreationRequestDTO;

public interface MovieService {
    String createMovie(MovieCreationRequestDTO request);


}
