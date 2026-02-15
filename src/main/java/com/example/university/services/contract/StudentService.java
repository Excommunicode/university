package com.example.university.services.contract;

import com.example.university.models.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student findStudent(Long id);

    List<Student> getAllStudents();

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    List<Student> getStudentsByCourse(Integer course);
}
