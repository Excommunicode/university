package com.example.university.services;

import com.example.university.dto.StudentCreateDto;
import com.example.university.dto.StudentResponseDto;
import com.example.university.dto.StudentUpdateDto;
import com.example.university.exception.DuplicateEmailException;
import com.example.university.exception.StudentNotFoundException;
import com.example.university.models.Student;
import com.example.university.repositories.StudentRepository;
import com.example.university.services.contract.StudentService;
import com.example.university.services.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    @Transactional
    public StudentResponseDto createStudent(StudentCreateDto dto) {
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("Student", dto.getEmail());
        }
        Student entity = studentMapper.toEntity(dto);
        Student saved = studentRepository.save(entity);
        return studentMapper.toResponseDto(saved);
    }

    @Override
    public StudentResponseDto findStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return studentMapper.toResponseDto(student);
    }

    @Override
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::toResponseDto);
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentUpdateDto dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        if (dto.getEmail() != null && studentRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateEmailException("Student", dto.getEmail());
        }
        studentMapper.applyPartialUpdate(existing, dto);
        Student saved = studentRepository.save(existing);
        return studentMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
}
