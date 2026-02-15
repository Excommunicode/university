package com.example.university.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateDto {

    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @Min(value = 1, message = "Course must be at least 1")
    private Integer course;
}
