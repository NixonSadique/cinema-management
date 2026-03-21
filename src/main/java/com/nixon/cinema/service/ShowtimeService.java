package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.ShowtimeCreationRequestDTO;
import com.nixon.cinema.dto.response.ShowtimeResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeService {
    String createShowtimesForMovie(ShowtimeCreationRequestDTO request);

    List<ShowtimeResponseDTO> getAllShowTimes();

    List<ShowtimeResponseDTO> getAllActiveShowTimes();

    List<ShowtimeResponseDTO> getAllByMovieId(Long id);

    List<ShowtimeResponseDTO> getAllByStartTime(LocalDateTime startTime);

    List<ShowtimeResponseDTO> getAllByRoomId(Long id);

    List<ShowtimeResponseDTO> getByActiveTrueAndStartTime(LocalDateTime startTime);

    List<ShowtimeResponseDTO> getByActiveTrueAndRoomId(Long id);

}
