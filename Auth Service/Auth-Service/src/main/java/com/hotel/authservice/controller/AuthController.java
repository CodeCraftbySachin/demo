package com.hotel.authservice.controller;

import com.hotel.authservice.dto.AuthResponseDto;
import com.hotel.authservice.dto.LoginRequestDto;
import com.hotel.authservice.dto.RegisterRequestDto;
import com.hotel.authservice.dto.UserResponseDto;
import com.hotel.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public UserResponseDto register(
            @RequestBody RegisterRequestDto requestDto) {

        return authService.register(requestDto);
    }

    @PostMapping("/login")
    public AuthResponseDto login(
            @RequestBody LoginRequestDto requestDto) {

        return authService.login(requestDto);
    }
}