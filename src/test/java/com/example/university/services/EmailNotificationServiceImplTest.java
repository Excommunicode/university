package com.example.university.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailNotificationServiceImplTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailNotificationServiceImpl service;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(service, "fromEmail", null);
        ReflectionTestUtils.setField(service, "smtpUsername", null);
    }

    @Test
    void sendUserWelcomeEmailUsesConfiguredAppFromAddress() {
        ReflectionTestUtils.setField(service, "fromEmail", "notifications@university.example");
        ReflectionTestUtils.setField(service, "smtpUsername", "smtp@university.example");

        service.sendUserWelcomeEmail("student@example.com", "John");

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertThat(sentMessage.getFrom()).isEqualTo("notifications@university.example");
        assertThat(sentMessage.getTo()).containsExactly("student@example.com");
        assertThat(sentMessage.getSubject()).isEqualTo("Welcome to University App");
        assertThat(sentMessage.getText()).contains("Hi John");
    }

    @Test
    void sendCustomEmailFallsBackToSmtpUsernameWhenAppFromInvalid() {
        ReflectionTestUtils.setField(service, "fromEmail", "''");
        ReflectionTestUtils.setField(service, "smtpUsername", "smtp@university.example");

        service.sendCustomEmail("student@example.com", "Message body");

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertThat(sentMessage.getFrom()).isEqualTo("smtp@university.example");
        assertThat(sentMessage.getSubject()).isEqualTo("University notification");
        assertThat(sentMessage.getText()).isEqualTo("Message body");
    }

    @Test
    void sendCustomEmailUsesDefaultFromAddressWhenConfiguredValuesInvalid() {
        ReflectionTestUtils.setField(service, "fromEmail", "not-an-email");
        ReflectionTestUtils.setField(service, "smtpUsername", "\"\"");

        service.sendCustomEmail("student@example.com", "Message body");

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertThat(sentMessage.getFrom()).isEqualTo("no-reply@university.local");
    }

    @Test
    void sendStudentWelcomeEmailDoesNotThrowWhenMailSenderFails() {
        ReflectionTestUtils.setField(service, "fromEmail", "notifications@university.example");
        doThrow(new RuntimeException("SMTP unavailable")).when(mailSender).send(any(SimpleMailMessage.class));

        assertDoesNotThrow(() -> service.sendStudentWelcomeEmail("student@example.com", "John Doe"));
    }
}
