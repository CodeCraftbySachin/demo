package com.hotel.authservice.feign;

import com.hotel.authservice.dto.AuthUserResponseDto;
import com.hotel.authservice.dto.UserRequestDto;
import com.hotel.authservice.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserFeignClient {

    @PostMapping("/users")
    UserResponseDto createUser(@RequestBody UserRequestDto requestDto);

    @GetMapping("/users/internal/email/{email}")
    AuthUserResponseDto getUserByEmail(@PathVariable String email);
}