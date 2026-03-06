package com.example.university.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateDto {

    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Email(message = "Email must be a valid format")
    @Size(max = 255)
    private String email;

    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 120, message = "Age must not exceed 120")
    private Integer age;
}
