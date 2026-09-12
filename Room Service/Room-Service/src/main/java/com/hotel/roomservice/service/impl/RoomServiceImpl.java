package com.hotel.roomservice.service.impl;

import com.hotel.roomservice.dto.RoomRequestDto;
import com.hotel.roomservice.dto.RoomResponseDto;
import com.hotel.roomservice.entity.Room;
import com.hotel.roomservice.entity.RoomType;
import com.hotel.roomservice.repository.RoomRepository;
import com.hotel.roomservice.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomResponseDto createRoom(
            RoomRequestDto requestDto) {

        roomRepository.findByRoomNumber(
                requestDto.getRoomNumber())
                .ifPresent(room -> {
                    throw new RuntimeException(
                            "Room already exists");
                });

        Room room = new Room();

        room.setRoomNumber(
                requestDto.getRoomNumber());

        room.setRoomType(
                RoomType.valueOf(
                        requestDto.getRoomType()
                                .toUpperCase()));

        room.setPrice(
                requestDto.getPrice());

        room.setAvailable(
                requestDto.getAvailable());

        Room savedRoom =
                roomRepository.save(room);

        return mapToResponse(savedRoom);
    }

    @Override
    public RoomResponseDto getRoomById(Long id) {

        Room room =
                roomRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Room not found"));

        return mapToResponse(room);
    }

    @Override
    public List<RoomResponseDto> getAllRooms() {

        return roomRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoomResponseDto updateRoom(
            Long id,
            RoomRequestDto requestDto) {

        Room room =
                roomRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Room not found"));

        room.setRoomNumber(
                requestDto.getRoomNumber());

        room.setRoomType(
                RoomType.valueOf(
                        requestDto.getRoomType()
                                .toUpperCase()));

        room.setPrice(
                requestDto.getPrice());

        room.setAvailable(
                requestDto.getAvailable());

        Room updatedRoom =
                roomRepository.save(room);

        return mapToResponse(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) {

        Room room =
                roomRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Room not found"));

        roomRepository.delete(room);
    }

    private RoomResponseDto mapToResponse(
            Room room) {

        RoomResponseDto responseDto =
                new RoomResponseDto();

        responseDto.setId(room.getId());
        responseDto.setRoomNumber(
                room.getRoomNumber());

        responseDto.setRoomType(
                room.getRoomType().name());

        responseDto.setPrice(
                room.getPrice());

        responseDto.setAvailable(
                room.getAvailable());

        return responseDto;
    }
}