package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.response.SeatResponseForRoom;
import com.nixon.cinema.repository.SeatRepository;
import com.nixon.cinema.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    @Override
    public List<SeatResponseForRoom> getAllSeatsByRoomId(Long roomId) {
        return seatRepository.findAllByRoomId(roomId).stream().map(
                seat ->
                        new SeatResponseForRoom(seat.getId(), seat.getSeatRow() + seat.getSeatNumber())
        ).toList();
    }
}
