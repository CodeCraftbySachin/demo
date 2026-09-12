package com.hotel.roomservice.controller;

import com.hotel.roomservice.dto.RoomRequestDto;
import com.hotel.roomservice.dto.RoomResponseDto;
import com.hotel.roomservice.service.RoomService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/test")
    public String test() {
        return "Room Service Running Successfully";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public RoomResponseDto createRoom(
            @Valid @RequestBody RoomRequestDto requestDto) {

        return roomService.createRoom(requestDto);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','GUEST')")
    public List<RoomResponseDto> getAllRooms() {

        return roomService.getAllRooms();
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','GUEST')")
    public RoomResponseDto getRoomById(
            @PathVariable Long id) {

        return roomService.getRoomById(id);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public RoomResponseDto updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRequestDto requestDto) {

        return roomService.updateRoom(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteRoom(
            @PathVariable Long id) {

        roomService.deleteRoom(id);

        return "Room deleted successfully";
    }
}