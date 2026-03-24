package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.SeatResponse;

import java.util.List;

public interface SeatService {
    List<SeatResponse> getAllSeatsByRoomId(Long roomId);


}
