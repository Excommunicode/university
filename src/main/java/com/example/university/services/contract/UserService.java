package com.example.university.services.contract;

import com.example.university.dto.UserLoginDto;
import com.example.university.dto.UserRegistrationDto;
import com.example.university.dto.UserResponseDto;

import java.util.UUID;

public interface UserService {

    UserResponseDto register(UserRegistrationDto dto);

    UserResponseDto findUserById(UUID userId);

    UserResponseDto login(UserLoginDto dto);
}
