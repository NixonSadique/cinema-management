package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.ShowtimeCreationRequest;
import com.nixon.cinema.dto.request.ShowtimeRequest;
import com.nixon.cinema.dto.response.MovieResponse;
import com.nixon.cinema.dto.response.SimpleRoomResponse;
import com.nixon.cinema.dto.response.ShowtimeResponse;
import com.nixon.cinema.exceptions.BadRequestException;
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

import java.time.OffsetDateTime;
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

            if (showtimeRepository.countOverlappingShowtime(room.getId(), showtime.startTime(), showtime.endTime()) != 0) {
                throw new BadRequestException("The room " + room.getId() + ", is occupied at the time chosen;");
            }

            var isTimeRangeLongerThanMovie = showtime.startTime().plusMinutes(movie.getDuration()).isBefore(showtime.endTime())
                    || showtime.startTime().plusMinutes(movie.getDuration()).isEqual(showtime.endTime());

            if ((showtime.endTime().isBefore(showtime.startTime())) || (!isTimeRangeLongerThanMovie)) {
                throw new BadRequestException("The end time is before the start time, or the duration is less than the movie duration");
            }

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

    private SimpleRoomResponse mapRoomToResponse(Room room) {
        return new SimpleRoomResponse(
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
    public List<ShowtimeResponse> getAllByStartTime(OffsetDateTime startTime) {
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
    public List<ShowtimeResponse> getByActiveTrueAndStartTime(OffsetDateTime startTime) {
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

    @Override
    public String deactivateShowtimes() {
        var showtimes = showtimeRepository.findByActiveTrueAndEndTimeLessThan(OffsetDateTime.now());
        showtimes.forEach(
                s -> s.setActive(false)
        );
        showtimeRepository.saveAll(showtimes);
        return "Expired showtimes deactivated!";
    }

}
