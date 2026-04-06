package com.example.university.services;

import com.example.university.dto.UserLoginDto;
import com.example.university.dto.UserRegistrationDto;
import com.example.university.dto.UserResponseDto;
import com.example.university.exception.BusinessRuleException;
import com.example.university.exception.DuplicateEmailException;
import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.User;
import com.example.university.repositories.UserRepository;
import com.example.university.security.JwtTokenProvider;
import com.example.university.services.contract.EmailNotificationService;
import com.example.university.services.contract.UserService;
import com.example.university.services.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtTokenProvider jwtTokenProvider;
    private final EmailNotificationService emailNotificationService;

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
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User saved = userRepository.save(user);
        UserResponseDto responseDto = userMapper.toResponseDto(saved);
        String token = jwtTokenProvider.generateToken(saved);
        responseDto.setToken(token);
        emailNotificationService.sendUserWelcomeEmail(saved.getEmail(), saved.getUsername());
        return responseDto;
    }

    @Override
    public UserResponseDto findUserById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto login(UserLoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BusinessRuleException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessRuleException("Invalid email or password");
        }

        UserResponseDto responseDto = userMapper.toResponseDto(user);
        String token = jwtTokenProvider.generateToken(user);
        responseDto.setToken(token);
        return responseDto;
    }
}
