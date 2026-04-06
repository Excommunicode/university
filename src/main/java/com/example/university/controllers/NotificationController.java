package com.example.university.controllers;

import com.example.university.dto.EmailNotificationRequestDto;
import com.example.university.services.contract.EmailNotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final EmailNotificationService emailNotificationService;

    @PostMapping("/email")
    public ResponseEntity<Map<String, String>> sendEmail(@Valid @RequestBody EmailNotificationRequestDto request) {
        emailNotificationService.sendCustomEmail(request.getEmail(), request.getMessage());
        return ResponseEntity.accepted().body(Map.of("status", "Email queued for sending"));
    }
}
