package com.example.university.services.contract;

import com.example.university.dto.StudentCreateDto;
import com.example.university.dto.StudentResponseDto;
import com.example.university.dto.StudentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

    StudentResponseDto createStudent(StudentCreateDto dto);

    StudentResponseDto findStudent(Long id);

    Page<StudentResponseDto> getAllStudents(Pageable pageable);

    StudentResponseDto updateStudent(Long id, StudentUpdateDto dto);

    void deleteStudent(Long id);
}
