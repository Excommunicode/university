package com.example.university.services;

import com.example.university.exception.BusinessRuleException;
import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Course;
import com.example.university.repositories.CourseRepository;
import com.example.university.services.contract.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    @Transactional
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional
    public Course updateCourse(Long id, Course course) {
        Course existing = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        existing.setTitle(course.getTitle());
        existing.setDuration(course.getDuration());
        return courseRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        if (course.getDuration() != null && course.getDuration() > 40) {
            throw new BusinessRuleException("Cannot delete course with duration greater than 40 hours");
        }
        courseRepository.delete(course);
    }
}
