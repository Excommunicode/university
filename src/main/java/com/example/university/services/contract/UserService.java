package com.example.university.services.contract;

import com.example.university.dto.UserCreateDto;
import com.example.university.dto.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDto createUser(UserCreateDto dto);

    UserResponseDto findUser(UUID userId);

    UserResponseDto updateUser(UUID userId, UserCreateDto dto);

    void deleteUser(UUID userId);

    List<UserResponseDto> getAllUsers(String email);
}
