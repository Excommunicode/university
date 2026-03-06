package com.example.university.services.mapper;

import com.example.university.dto.StudentCreateDto;
import com.example.university.dto.StudentResponseDto;
import com.example.university.dto.StudentUpdateDto;
import com.example.university.models.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public Student toEntity(StudentCreateDto dto) {
        if (dto == null) {
            return null;
        }
        Student entity = new Student();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setAge(dto.getAge());
        return entity;
    }

    public void applyPartialUpdate(Student entity, StudentUpdateDto dto) {
        if (dto == null) {
            return;
        }
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getAge() != null) {
            entity.setAge(dto.getAge());
        }
        // Creation timestamp is never overwritten
    }

    public StudentResponseDto toResponseDto(Student entity) {
        if (entity == null) {
            return null;
        }
        return new StudentResponseDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getAge(),
                entity.getCreatedAt()
        );
    }

    public List<StudentResponseDto> toResponseDtoList(List<Student> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
}
