package com.hotel.roomservice.service;

import com.hotel.roomservice.dto.RoomRequestDto;
import com.hotel.roomservice.dto.RoomResponseDto;

import java.util.List;

public interface RoomService {

    RoomResponseDto createRoom(RoomRequestDto requestDto);
    RoomResponseDto getRoomById(Long id);
    List<RoomResponseDto> getAllRooms();
    RoomResponseDto updateRoom(Long id, RoomRequestDto requestDto);
    void deleteRoom(Long id);
}