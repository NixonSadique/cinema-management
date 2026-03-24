package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.RoomCreationRequest;
import com.nixon.cinema.dto.response.RoomResponse;
import com.nixon.cinema.dto.response.SeatResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.exceptions.EntityNotFoundException;
import com.nixon.cinema.model.Room;
import com.nixon.cinema.model.Seat;
import com.nixon.cinema.model.enums.RoomType;
import com.nixon.cinema.repository.RoomRepository;
import com.nixon.cinema.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

        List<Seat> seats = new ArrayList<>();

        for (int i = 0; i < request.capacity(); i++) {
            if ((i != 0) && (i % request.columnNumber() == 0)) {
                row++;
                seatNum = 0;
                System.out.println("New Row: " + row);
            }
            seatNum++;

            Seat seat = new Seat();
            seat.setRoom(rawRoom);
            seat.setSeatRow(String.valueOf(row));
            seat.setSeatNumber(seatNum);

            System.out.println("Seat: " + row + seatNum);

            seats.add(seat);
        }

        rawRoom.setSeat(seats);
        Room savedRoom = roomRepository.save(rawRoom);

        System.out.println(savedRoom);

        return new RoomResponse(savedRoom.getId(),
                savedRoom.getName(),
                savedRoom.getRoomType(),
                savedRoom.getSeat().stream().map(
                        seat -> new SeatResponse(seat.getId(), seat.getSeatRow() + seat.getSeatNumber())
                ).toList());
    }

    @Override
    public RoomResponse getRoomByName(String name) {
        return roomRepository.findByName(name).map(
                room -> new RoomResponse(room.getId(), room.getName(), room.getRoomType(), room.getSeat().stream().map(
                        seat -> new SeatResponse(seat.getId(), seat.getSeatRow() + seat.getSeatNumber())
                ).toList())
        ).orElseThrow(
                () -> new EntityNotFoundException("Room with name " + name + " not found")
        );
    }

    @Override
    public List<RoomResponse> getRoomByType(RoomType type) {
        return roomRepository.findAllByRoomType(type).stream().map(
                room -> new RoomResponse(room.getId(), room.getName(), room.getRoomType(), room.getSeat().stream().map(
                        seat -> new SeatResponse(seat.getId(), seat.getSeatRow() + seat.getSeatNumber())
                ).toList())
        ).toList();
    }
}
