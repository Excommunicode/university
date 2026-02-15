package com.example.university.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoCreateDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    private Boolean completed = false;
}
