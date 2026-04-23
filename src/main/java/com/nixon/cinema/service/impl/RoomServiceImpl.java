package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.RoomCreationRequest;
import com.nixon.cinema.dto.response.RoomResponse;
import com.nixon.cinema.dto.response.SeatResponse;
import com.nixon.cinema.dto.response.SimpleRoomResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.Room;
import com.nixon.cinema.model.Seat;
import com.nixon.cinema.model.enums.RoomType;
import com.nixon.cinema.repository.RoomRepository;
import com.nixon.cinema.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomResponse createRoom(RoomCreationRequest request) {
        char row = 'A';
        int seatNum = 0;

        if (roomRepository.findByName(request.name()).isPresent()) {
            throw new BadRequestException("Room already exists");
        }

        Room rawRoom = new Room();
        rawRoom.setName(request.name());
        rawRoom.setRoomType(request.type() != null ? request.type() : RoomType.ROOM_2D);

        List<Seat> seats = getSeats(request, row, seatNum, rawRoom);

        rawRoom.setSeat(seats);
        Room savedRoom = roomRepository.save(rawRoom);

        log.info("Room '{}' saved successfully", savedRoom.getName());

        return new RoomResponse(savedRoom.getId(),
                savedRoom.getName(),
                savedRoom.getRoomType(),
                savedRoom.getSeat().stream().map(
                        seat -> new SeatResponse(seat.getId(), seat.getSeatRow() + seat.getSeatNumber())
                ).toList());
    }

    private static @NonNull List<Seat> getSeats(RoomCreationRequest request, char row, int seatNum, Room rawRoom) {
        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < request.capacity(); i++) {
            boolean isNewRow = (i > 0) && (i % request.rowNumber() == 0);
            if (isNewRow) {
                row++;
                seatNum = 0;
            }
            seatNum++;

            Seat seat = new Seat();
            seat.setRoom(rawRoom);
            seat.setSeatRow(String.valueOf(row));
            seat.setSeatNumber(seatNum);

            log.debug("Seat: {}{}", row, seatNum);

            seats.add(seat);
        }
        return seats;
    }

    @Override
    public RoomResponse getRoomByName(String name) {
        log.info("Get room by name: {}", name);
        return roomRepository.findByName(name).map(
                room -> new RoomResponse(room.getId(), room.getName(), room.getRoomType(), room.getSeat().stream().map(
                        seat -> new SeatResponse(seat.getId(), seat.getSeatRow() + seat.getSeatNumber())
                ).toList())
        ).orElseThrow(
                () -> {
                    log.warn("Room not found by name: {}", name);
                    return new EntityNotFoundException("Room with name " + name + " not found");
                }
        );
    }

    @Override
    public List<SimpleRoomResponse> getRoomByType(RoomType type) {
        log.info("Get room by type: {}", type);
        return roomRepository.findAllByRoomType(type).stream().map(
                room -> new SimpleRoomResponse(room.getId(), room.getName(), room.getRoomType(), room.getSeat().size())
        ).toList();
    }
}
