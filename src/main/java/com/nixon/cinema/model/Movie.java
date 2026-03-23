package com.nixon.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Service
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int duration;

    private LocalDateTime releaseDate;

    private String ageRating;

    private Set<String> production;
    private Set<String> director;
    private Set<String> mainCast;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;
}
