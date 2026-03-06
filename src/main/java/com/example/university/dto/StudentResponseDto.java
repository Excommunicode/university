package com.example.university.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private Instant createdAt;
}
