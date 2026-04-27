package com.example.university.services;

import com.example.university.services.contract.EmailNotificationService;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:}")
    private String fromEmail;

    @Value("${spring.mail.username:}")
    private String smtpUsername;

    @Override
    @Async
    public void sendUserWelcomeEmail(String email, String username) {
        log.info("sendUserWelcomeEmail called: email='{}', username='{}'", email, username);
        sendEmail(
                email,
                "Welcome to University App",
                "Hi " + username + ",\n\nYour account has been created successfully.\n\nBest regards,\nUniversity Team"
        );
    }

    @Override
    @Async
    public void sendStudentWelcomeEmail(String email, String fullName) {
        log.info("sendStudentWelcomeEmail called: email='{}', fullName='{}'", email, fullName);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        sendEmail(
                email,
                "Student profile created",
                "Hi " + fullName + ",\n\nYour student profile has been created successfully.\n\nBest regards,\nUniversity Team"
        );
        stopWatch.stop();
        log.debug("sendStudentWelcomeEmail executed in {} ms", stopWatch.getTotalTimeMillis());
    }

    @Override
    @Async
    public void sendCustomEmail(String email, String message) {
        log.info("sendCustomEmail called: email='{}', messageLength={}", email, message != null ? message.length() : 0);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        sendEmail(
                email,
                "University notification",
                message
        );
        stopWatch.stop();
        log.debug("sendCustomEmail executed in {} ms", stopWatch.getTotalTimeMillis());
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(resolveFromAddress());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            log.info("Email notification sent to {}", to);
        } catch (Exception ex) {
            log.error("Failed to send email notification to {}", to, ex);
        }
    }

    private String resolveFromAddress() {
        String appFrom = sanitizeAddress(fromEmail);
        if (isValidEmailAddress(appFrom)) {
            return appFrom;
        }

        String smtpFrom = sanitizeAddress(smtpUsername);
        if (isValidEmailAddress(smtpFrom)) {
            return smtpFrom;
        }

        return "no-reply@university.local";
    }

    private String sanitizeAddress(String value) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        String sanitized = value.trim();

        // Some env setups pass empty strings as '' or "".
        if ("''".equals(sanitized) || "\"\"".equals(sanitized)) {
            return null;
        }

        return sanitized;
    }

    private boolean isValidEmailAddress(String email) {
        if (!StringUtils.hasText(email)) {
            return false;
        }

        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            return true;
        } catch (AddressException ex) {
            return false;
        }
    }
}
