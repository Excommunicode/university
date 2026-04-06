package com.example.university.services.contract;

public interface EmailNotificationService {
    void sendUserWelcomeEmail(String email, String username);
    void sendStudentWelcomeEmail(String email, String fullName);
    void sendCustomEmail(String email, String message);
}
