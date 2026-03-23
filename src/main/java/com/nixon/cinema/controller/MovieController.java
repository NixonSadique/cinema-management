package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.MovieCreationRequest;
import com.nixon.cinema.dto.response.MovieResponse;
import com.nixon.cinema.service.MovieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cinema/v1")
@RequiredArgsConstructor
@Tag(name = "6.Movie Controller", description = "Contains the endpoints for the Movie creation, retrieval and modifications")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    ResponseEntity<List<MovieResponse>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PostMapping("/movies")
    ResponseEntity<String> createMovie(MovieCreationRequest request) {
        return new ResponseEntity<>(movieService.createMovie(request), HttpStatus.CREATED);
    }
}