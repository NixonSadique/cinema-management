package com.nixon.cinema.service.impl;

import com.nixon.cinema.dto.request.RoomCreationRequest;
import com.nixon.cinema.dto.response.RoomResponse;
import com.nixon.cinema.exceptions.BadRequestException;
import com.nixon.cinema.model.Room;
import com.nixon.cinema.model.enums.RoomType;
import com.nixon.cinema.repository.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomServiceImpl;

    @Test
    void createRoom_whenSuccessful_returnRoomResponse() {
        RoomCreationRequest request = new RoomCreationRequest(
                "Room 1",
                RoomType.ROOM_3D,
                50,
                5
        );

        when(roomRepository.findByName(request.name())).thenReturn(Optional.empty());
        when(roomRepository.save(any(Room.class))).then(
                invocation -> {
                    Room room = invocation.getArgument(0);
                    room.setId(1L);
                    return room;
                });

        RoomResponse response = roomServiceImpl.createRoom(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Room 1", response.name());
        assertEquals(RoomType.ROOM_3D, response.roomType());
        assertEquals(50, response.seats().size());

        assertEquals("A1", response.seats().getFirst().seat());
        assertEquals("B1", response.seats().get(5).seat());
        assertEquals("C1", response.seats().get(10).seat());
    }

    @Test
    void createRoom_whenRoomTypeNotPresent_returnRoomResponse() {
        RoomCreationRequest request = new RoomCreationRequest(
                "Room 1",
                null,
                50,
                5
        );

        when(roomRepository.findByName(request.name())).thenReturn(Optional.empty());
        when(roomRepository.save(any(Room.class))).then(
                invocation -> invocation.getArgument(0));

        RoomResponse response = roomServiceImpl.createRoom(request);

        assertNotNull(response);
        assertEquals(RoomType.ROOM_2D, response.roomType());
        assertEquals(50, response.seats().size());
    }

    @Test
    void createRoom_whenRoomExists_throwException() {
        Room room = new Room();
        RoomCreationRequest request = new RoomCreationRequest(
                "Room 1",
                null,
                null,
                null
        );
        room.setName("Room 1");

        when(roomRepository.findByName(request.name())).thenReturn(Optional.of(room));

        assertThrows(BadRequestException.class, () -> roomServiceImpl.createRoom(request));

    }

}