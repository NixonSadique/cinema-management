package com.nixon.cinema.service;

import com.nixon.cinema.dto.request.ShowtimeCreationRequest;
import com.nixon.cinema.dto.response.ShowtimeResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeService {
    String createShowtimesForMovie(ShowtimeCreationRequest request);

    List<ShowtimeResponse> getAllShowTimes();

    List<ShowtimeResponse> getAllActiveShowTimes();

    List<ShowtimeResponse> getAllByMovieId(Long id);

    List<ShowtimeResponse> getAllByStartTime(LocalDateTime startTime);

    List<ShowtimeResponse> getAllByRoomId(Long id);

    List<ShowtimeResponse> getByActiveTrueAndStartTime(LocalDateTime startTime);

    List<ShowtimeResponse> getByActiveTrueAndRoomId(Long id);

}
