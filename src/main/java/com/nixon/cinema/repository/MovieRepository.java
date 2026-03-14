package com.nixon.cinema.repository;

import com.nixon.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByOrderByReleaseDateDesc();

    List<Movie> findAllByTitleContainingIgnoreCase(String title);

    Optional<Movie> findByTitleIgnoreCase(String title);

    List<Movie> findByOrderByAgeRatingDesc();

}
