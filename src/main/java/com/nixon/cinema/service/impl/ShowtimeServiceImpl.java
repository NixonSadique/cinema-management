package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.ShowtimeCreationRequest;
import com.nixon.cinema.dto.request.ShowtimeRequest;
import com.nixon.cinema.dto.response.MovieResponse;
import com.nixon.cinema.dto.response.RoomResponseForShowtime;
import com.nixon.cinema.dto.response.ShowtimeResponse;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.Movie;
import com.nixon.cinema.model.Room;
import com.nixon.cinema.model.Showtime;
import com.nixon.cinema.repository.MovieRepository;
import com.nixon.cinema.repository.RoomRepository;
import com.nixon.cinema.repository.ShowtimeRepository;
import com.nixon.cinema.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;

    @Override
    public String createShowtimesForMovie(ShowtimeCreationRequest request) {
        var movie = movieRepository.findById(request.movieId()).orElseThrow(
                () -> new EntityNotFoundException("Movie with id: " + request.movieId() + " not found")
        );

        List<Showtime> showtimeList = new ArrayList<>();

        for (ShowtimeRequest showtime : request.showtimes()) {
            var room = roomRepository.findById(showtime.roomId()).orElseThrow(
                    () -> new EntityNotFoundException("The room with ID" + showtime.roomId() + " not found!")
            );

            Showtime rawShowtime = new Showtime();
            rawShowtime.setMovie(movie);
            rawShowtime.setRoom(room);
            rawShowtime.setActive(true);
            rawShowtime.setPrice(showtime.price());
            rawShowtime.setStartTime(showtime.startTime());
            rawShowtime.setEndTime(showtime.endTime());

            showtimeList.add(rawShowtime);
        }

        showtimeRepository.saveAll(showtimeList);

        return "Showtimes for the movie created successfully!";
    }

    /**
     * TODO: Implement the DTO response
     *
     * @return
     */

    @Override
    public List<ShowtimeResponse> getAllShowTimes() {
        return showtimeRepository.findAll().stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }

    private RoomResponseForShowtime mapRoomToResponse(Room room) {
        return new RoomResponseForShowtime(
                room.getId(),
                room.getName(),
                room.getRoomType(),
                room.getSeat().size()
        );
    }

    private MovieResponse mapMovieToResponse(Movie movie) {
        return new MovieResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getDescription(),
                movie.getAgeRating(),
                movie.getDuration(),
                movie.getProduction(),
                movie.getDirector(),
                movie.getMainCast(),
                movie.getReleaseDate()
        );
    }

    @Override
    public List<ShowtimeResponse> getAllActiveShowTimes() {
        return showtimeRepository.findAllByActiveTrue().stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }

    @Override
    public List<ShowtimeResponse> getAllByMovieId(Long id) {
        return showtimeRepository.findAllByMovieId(id).stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }

    @Override
    public List<ShowtimeResponse> getAllByStartTime(LocalDateTime startTime) {
        return showtimeRepository.findAllByStartTime(startTime).stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }

    @Override
    public List<ShowtimeResponse> getAllByRoomId(Long id) {
        return showtimeRepository.findAllByRoomId(id).stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }

    @Override
    public List<ShowtimeResponse> getByActiveTrueAndStartTime(LocalDateTime startTime) {
        return showtimeRepository.findByActiveTrueAndStartTime(startTime).stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }

    @Override
    public List<ShowtimeResponse> getByActiveTrueAndRoomId(Long id) {
        return showtimeRepository.findByActiveTrueAndRoomId(id).stream().map(
                showtime -> new ShowtimeResponse(
                        showtime.getId(),
                        showtime.getStartTime(),
                        showtime.getEndTime(),
                        showtime.getPrice(),
                        mapMovieToResponse(showtime.getMovie()),
                        mapRoomToResponse(showtime.getRoom())
                )
        ).toList();
    }
}
