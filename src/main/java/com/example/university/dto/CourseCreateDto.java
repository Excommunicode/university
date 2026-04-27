package com.example.university.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreateDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    @NotNull(message = "Duration must not be null")
    @Min(value = 1, message = "Duration must be at least 1 hour")
    private Integer duration;
}
