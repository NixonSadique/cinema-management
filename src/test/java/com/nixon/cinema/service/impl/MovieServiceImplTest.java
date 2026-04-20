package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.MovieCreationRequest;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.model.Movie;
import com.nixon.cinema.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    MovieServiceImpl movieService;

    @Test
    void createMovie_success_returnsMessage() {
        MovieCreationRequest request = new MovieCreationRequest(
                "title",
                "description",
                "rating",
                120,
                List.of(),
                List.of(),
                List.of(),
                OffsetDateTime.now()
        );

        when(movieRepository.findByTitleIgnoreCase(request.title())).thenReturn(Optional.empty());

        var response = movieService.createMovie(request);
        assertNotNull(response);
        assertEquals("Movie created", response);

        verify(movieRepository).findByTitleIgnoreCase(request.title());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void createMovie_whenMovieAlreadyExists_throwsException() {
        MovieCreationRequest request = new MovieCreationRequest(
                "title",
                "description",
                "rating",
                120,
                List.of(),
                List.of(),
                List.of(),
                OffsetDateTime.now()
        );

        Movie movie = new Movie();
        movie.setTitle(request.title());
        movie.setDescription(request.description());
        movie.setDuration(request.duration());
        when(movieRepository.findByTitleIgnoreCase(request.title())).thenReturn(Optional.of(movie));

        assertThrows(BadRequestException.class, () -> movieService.createMovie(request));

        verify(movieRepository).findByTitleIgnoreCase(request.title());
    }
}