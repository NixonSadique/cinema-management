package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.MovieCreationRequest;
import com.nixon.cinema.dto.response.MovieResponse;

import java.util.List;

public interface MovieService {
    String createMovie(MovieCreationRequest request);

    List<MovieResponse> getAllMovies();


}
