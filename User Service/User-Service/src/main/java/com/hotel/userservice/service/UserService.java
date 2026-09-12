package com.hotel.userservice.service;

import java.util.List;

import com.hotel.userservice.dto.AuthUserResponseDto;
import com.hotel.userservice.dto.UserRequestDto;
import com.hotel.userservice.dto.UserResponseDto;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);
    UserResponseDto getUserById(Long id);
    UserResponseDto getUserByEmail(String email);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserRequestDto userRequestDto);
    AuthUserResponseDto getUserForAuthentication(String email);
    void deleteUser(Long id);
}