package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.MovieCreationRequestDTO;
import com.nixon.cinema.dto.response.MovieResponseDTO;

import java.util.List;

public interface MovieService {
    String createMovie(MovieCreationRequestDTO request);

    List<MovieResponseDTO> getAllMovies();


}
