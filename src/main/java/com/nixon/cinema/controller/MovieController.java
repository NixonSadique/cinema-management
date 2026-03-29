package com.nixon.cinema.controller;

import com.nixon.cinema.dto.request.MovieCreationRequest;
import com.nixon.cinema.dto.response.MovieResponse;
import com.nixon.cinema.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cinema/v1")
@RequiredArgsConstructor
@Tag(name = "6.Movie Controller", description = "Contains the endpoints for the Movie creation and retrieval")
public class MovieController {

    private final MovieService movieService;

    @Operation(
            summary = "Get Movies",
            description = "Get All movies."
    )
    @GetMapping("/movies")
    ResponseEntity<List<MovieResponse>> getMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @Operation(
            summary = "Create movie",
            description = "Create movies"
    )
    @PostMapping("/movies")
    ResponseEntity<String> createMovie(@RequestBody MovieCreationRequest request) {
        return new ResponseEntity<>(movieService.createMovie(request), HttpStatus.CREATED);
    }
}