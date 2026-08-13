package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.MovieCreationRequest;
import com.nixon.cinema.dto.response.MovieResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.model.Movie;
import com.nixon.cinema.repository.MovieRepository;
import com.nixon.cinema.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    @CacheEvict(value = "movie", allEntries = true)
    public String createMovie(MovieCreationRequest request) {

        if (movieRepository.findByTitleIgnoreCase(request.title()).isPresent()) {
            throw new BadRequestException("A movie with the title %s already exists!".formatted(request.title()));
        }

        Movie movie = new Movie();
        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setDuration(request.duration());
        movie.setAgeRating(request.ageRating());
        movie.setReleaseDate(request.releaseDate());
        movie.setMainCast(request.mainCast());
        movie.setDirector(request.director());
        movie.setProduction(request.production());

        movieRepository.save(movie);
        return "Movie created";
    }

    @Override
    @Cacheable(value = "movie")
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream().map(
                movie -> new MovieResponse(movie.getId(), movie.getTitle(),
                        movie.getDescription(), movie.getAgeRating(), movie.getDuration(),
                        new ArrayList<>(movie.getProduction()),
                        new ArrayList<>(movie.getDirector()),
                        new ArrayList<>(movie.getMainCast()),
                        movie.getReleaseDate()
                )
        ).collect(Collectors.toCollection(ArrayList::new));
    }
}
