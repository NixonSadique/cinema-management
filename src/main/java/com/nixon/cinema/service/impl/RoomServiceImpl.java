package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.RoomCreationRequestDTO;
import com.nixon.cinema.dto.response.RoomResponseDTO;
import com.nixon.cinema.dto.response.SeatResponseForRoomDTO;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    @Override
    public RoomResponseDTO createRoom(RoomCreationRequestDTO request) {
        char row = 'A';
        int seatNum = 0;
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

        Room savedRoom = roomRepository.save(rawRoom);

        System.out.println(savedRoom);

        return Optional.of(savedRoom).map(
                r -> new RoomResponseDTO(r.getId(), r.getName(), r.getRoomType(), r.getSeat().stream().map(
                        seat -> new SeatResponseForRoomDTO(seat.getId(), seat.getSeatRow() + seat.getSeatNumber() + seat)
                ).toList())
        ).orElseThrow(
                () -> new BadRequestException("Room could not be saved")
        );
    }

    @Override
    public RoomResponseDTO getRoomByName(String name) {
        return roomRepository.findByName(name).map(
                room -> new RoomResponseDTO(room.getId(), room.getName(), room.getRoomType(), room.getSeat().stream().map(
                        seat -> new SeatResponseForRoomDTO(seat.getId(), seat.getSeatRow() + seat.getSeatNumber() + seat)
                ).toList())
        ).orElseThrow(
                () -> new EntityNotFoundException("Room with name " + name + " not found")
        );
    }

    @Override
    public List<RoomResponseDTO> getRoomByType(RoomType type) {
        return roomRepository.findAllByRoomType(type).stream().map(
                room -> new RoomResponseDTO(room.getId(), room.getName(), room.getRoomType(), room.getSeat().stream().map(
                        seat -> new SeatResponseForRoomDTO(seat.getId(), seat.getSeatRow() + seat.getSeatNumber() + seat)
                ).toList())
        ).toList();
    }
}
