package com.example.university.services.contract;

import com.example.university.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Course createCourse(Course course);

    Course findCourse(Long id);

    Page<Course> getAllCourses(Pageable pageable);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);
}
