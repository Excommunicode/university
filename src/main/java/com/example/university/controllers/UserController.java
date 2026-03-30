package com.example.university.controllers;

import com.example.university.dto.UserLoginDto;
import com.example.university.dto.UserRegistrationDto;
import com.example.university.dto.UserResponseDto;
import com.example.university.services.contract.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRegistrationDto dto) {
        UserResponseDto created = userService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@Valid @RequestBody UserLoginDto dto) {
        UserResponseDto user = userService.login(dto);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.findUserById(userId));
    }
}
