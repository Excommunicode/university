package com.example.university.controllers;

import com.example.university.dto.CourseCreateDto;
import com.example.university.models.Course;
import com.example.university.services.contract.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseCreateDto dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDuration(dto.getDuration());
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.createCourse(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findCourse(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.findCourse(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Course> getAllCourses(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id,
                                               @Valid @RequestBody CourseCreateDto dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDuration(dto.getDuration());
        return ResponseEntity.ok(courseService.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
