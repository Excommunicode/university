package com.example.university.services.contract;

import com.example.university.models.Course;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    Course findCourse(Long id);

    List<Course> getAllCourses();

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);
}
