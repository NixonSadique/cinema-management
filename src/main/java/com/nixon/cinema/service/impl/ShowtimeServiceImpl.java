package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.ShowtimeCreationRequestDTO;
import com.nixon.cinema.dto.response.ShowtimeResponseDTO;
import com.nixon.cinema.exceptions.EntityNotFoundException;
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
    public String createShowtimesForMovie(ShowtimeCreationRequestDTO request) {
        var movie = movieRepository.findById(request.movieId()).orElseThrow(
                () -> new EntityNotFoundException("Movie with id: " + request.movieId() + " not found")
        );

        List<Long> notFoundRoomIds = new ArrayList<>();
        List<Showtime> showtimeList = new ArrayList<>();

        request.showtime().forEach(
                showtime -> {
                    var room = roomRepository.findById(showtime.roomId()).orElse(null);
                    if (room == null) {
                        notFoundRoomIds.add(room.getId());
                    } else {
                        Showtime rawShowtime = new Showtime();
                        rawShowtime.setMovie(movie);
                        rawShowtime.setRoom(room);
                        rawShowtime.setActive(true);
                        rawShowtime.setStartTime(showtime.startTime());
                        rawShowtime.setEndTime(showtime.endTime());
                        showtimeList.add(rawShowtime);
                    }
                }
        );

        showtimeRepository.saveAll(showtimeList);

        var message = "Showtimes for " + movie.getTitle() + "are now saved!";
        var errors = "The Following Room Id's were not found: ";
        notFoundRoomIds.forEach(roomId -> {
            errors.concat("-" + roomId + "-");
        });

        if (!notFoundRoomIds.isEmpty()) {
            message.concat(errors);
        }

        return message;
    }

    /**
     * TODO: Implement the DTO response
     *
     * @return
     */

    @Override
    public List<ShowtimeResponseDTO> getAllShowTimes() {
        return showtimeRepository.findAll().stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }

    @Override
    public List<ShowtimeResponseDTO> getAllActiveShowTimes() {
        return showtimeRepository.findAllByActiveTrue().stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }

    @Override
    public List<ShowtimeResponseDTO> getAllByMovieId(Long id) {
        return showtimeRepository.findAllByMovieId(id).stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }

    @Override
    public List<ShowtimeResponseDTO> getAllByStartTime(LocalDateTime startTime) {
        return showtimeRepository.findAllByStartTime(startTime).stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }

    @Override
    public List<ShowtimeResponseDTO> getAllByRoomId(Long id) {
        return showtimeRepository.findAllByRoomId(id).stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }

    @Override
    public List<ShowtimeResponseDTO> getByActiveTrueAndStartTime(LocalDateTime startTime) {
        return showtimeRepository.findByActiveTrueAndStartTime(startTime).stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }

    @Override
    public List<ShowtimeResponseDTO> getByActiveTrueAndRoomId(Long id) {
        return showtimeRepository.findByActiveTrueAndRoomId(id).stream().map(
                showtime -> new ShowtimeResponseDTO()
        ).toList();
    }
}
