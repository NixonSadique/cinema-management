package com.nixon.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Getter
@Setter
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

    private OffsetDateTime releaseDate;

    private String ageRating;

    @ElementCollection
    private List<String> production;

    @ElementCollection
    private List<String> director;

    @ElementCollection
    private List<String> mainCast;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;
}
