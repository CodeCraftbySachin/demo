package com.hotel.userservice.controller;

import com.hotel.userservice.dto.AuthUserResponseDto;
import com.hotel.userservice.dto.UserRequestDto;
import com.hotel.userservice.dto.UserResponseDto;
import com.hotel.userservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    public String test() {
        return "User Service Running Successfully";
    }

    /*
     * PUBLIC
     * Used by Auth Service registration
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDto createUser(
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.createUser(requestDto);
    }

    /*
     * INTERNAL
     * Used by Auth Service login
     */
    @GetMapping("/internal/email/{email}")
    public AuthUserResponseDto getUserForAuthentication(
            @PathVariable String email) {

        return userService.getUserForAuthentication(email);
    }

    /*
     * SECURED APIs
     */

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF','GUEST')")
    public UserResponseDto getUserById(
            @PathVariable Long id) {

        return userService.getUserById(id);
    }

    @GetMapping
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public List<UserResponseDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.updateUser(id, requestDto);
    }

    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }
}