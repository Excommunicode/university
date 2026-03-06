package com.example.university.services;

import com.example.university.dto.UserRegistrationDto;
import com.example.university.dto.UserResponseDto;
import com.example.university.exception.BusinessRuleException;
import com.example.university.exception.DuplicateEmailException;
import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.User;
import com.example.university.repositories.UserRepository;
import com.example.university.services.contract.UserService;
import com.example.university.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDto register(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("User", dto.getEmail());
        }
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessRuleException("Username '" + dto.getUsername() + "' is already taken");
        }
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    @Override
    public UserResponseDto findUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toResponseDto(user);
    }
}
