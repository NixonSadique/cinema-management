package com.nixon.cinema.service;

import com.nixon.cinema.dto.response.SeatResponseForRoom;

import java.util.List;

public interface SeatService {
    List<SeatResponseForRoom> getAllSeatsByRoomId(Long roomId);


}
