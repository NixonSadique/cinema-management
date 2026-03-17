package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.SeatResponseForRoomDTO;

import java.util.List;

public interface SeatService {
    List<SeatResponseForRoomDTO> getAllSeatsByRoomId(Long roomId);


}
