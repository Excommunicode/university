package com.example.university.services.mapper;

import com.example.university.dto.UserRegistrationDto;
import com.example.university.dto.UserResponseDto;
import com.example.university.models.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAge(),
                ""
        );
    }

    public User toEntity(UserRegistrationDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setAge(dto.getAge());
        return user;
    }

    public List<UserResponseDto> toResponseDtoList(List<User> users) {
        if (users == null) {
            return List.of();
        }
        return users.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
