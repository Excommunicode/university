package com.example.university.services;

import com.example.university.dto.UserCreateDto;
import com.example.university.dto.UserResponseDto;
import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.User;
import com.example.university.repositories.UserRepository;
import com.example.university.services.contract.UserService;
import com.example.university.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserCreateDto dto) {
        User user = userMapper.toEntity(dto);
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto findUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toResponseDto(user);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UUID userId, UserCreateDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public List<UserResponseDto> getAllUsers(String email) {
        if (email != null && !email.isEmpty()) {
            return userMapper.toResponseDtoList(userRepository.findUserByEmail(email));
        }
        return userMapper.toResponseDtoList(userRepository.findAll());
    }
}
