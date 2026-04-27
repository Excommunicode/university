package com.example.university.services;

import com.example.university.dto.StudentCreateDto;
import com.example.university.dto.StudentResponseDto;
import com.example.university.dto.StudentUpdateDto;
import com.example.university.exception.DuplicateEmailException;
import com.example.university.exception.ResourceNotFoundException;
import com.example.university.exception.StudentNotFoundException;
import com.example.university.models.Course;
import com.example.university.models.Student;
import com.example.university.repositories.CourseRepository;
import com.example.university.repositories.StudentRepository;
import com.example.university.services.contract.EmailNotificationService;
import com.example.university.services.contract.StudentService;
import com.example.university.services.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final EmailNotificationService emailNotificationService;

    @Override
    @Transactional
    public StudentResponseDto createStudent(StudentCreateDto dto) {
        log.info("createStudent request: email='{}'", dto.getEmail());
        if (studentRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("Student", dto.getEmail());
        }
        Student entity = studentMapper.toEntity(dto);
        entity.setCourse(resolveCourse(dto.getCourseId()));
        Student saved = studentRepository.save(entity);
        String fullName = saved.getFirstName() + " " + saved.getLastName();
        emailNotificationService.sendStudentWelcomeEmail(saved.getEmail(), fullName);
        return studentMapper.toResponseDto(saved);
    }

    @Override
    public StudentResponseDto findStudent(Long id) {
        log.info("findStudent called: id={}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return studentMapper.toResponseDto(student);
    }

    @Override
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
        log.info("getAllStudents called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return studentRepository.findAll(pageable).map(studentMapper::toResponseDto);
    }

    @Override
    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentUpdateDto dto) {
        log.info("updateStudent called: id={}, email='{}'", id, dto.getEmail());
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        if (dto.getEmail() != null && studentRepository.existsByEmailAndIdNot(dto.getEmail(), id)) {
            throw new DuplicateEmailException("Student", dto.getEmail());
        }
        studentMapper.applyPartialUpdate(existing, dto);
        if (dto.getCourseId() != null) {
            existing.setCourse(resolveCourse(dto.getCourseId()));
        }
        Student saved = studentRepository.save(existing);
        return studentMapper.toResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        log.info("deleteStudent called: id={}", id);
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    private Course resolveCourse(Long courseId) {
        if (courseId == null) {
            return null;
        }

        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));
    }
}
