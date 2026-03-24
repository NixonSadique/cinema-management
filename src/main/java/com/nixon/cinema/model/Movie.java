package com.nixon.cinema.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

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

    @ElementCollection()
    private Set<String> production;

    @ElementCollection
    private Set<String> director;

    @ElementCollection
    private Set<String> mainCast;

    @OneToMany(mappedBy = "movie")
    private List<Showtime> showtimes;
}
