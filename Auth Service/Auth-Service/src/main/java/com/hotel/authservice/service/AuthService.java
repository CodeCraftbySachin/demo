package com.hotel.authservice.service;

import com.hotel.authservice.dto.AuthResponseDto;
import com.hotel.authservice.dto.LoginRequestDto;
import com.hotel.authservice.dto.RegisterRequestDto;
import com.hotel.authservice.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegisterRequestDto requestDto);
    AuthResponseDto login(LoginRequestDto requestDto);
}