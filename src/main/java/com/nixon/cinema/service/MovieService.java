package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.MovieCreationRequestDTO;
import com.nixon.cinema.dto.request.PurchaseRequestDTO;
import com.nixon.cinema.model.Movie;

import java.util.List;

public interface MovieService {
    String createMovie(MovieCreationRequestDTO request);


}
