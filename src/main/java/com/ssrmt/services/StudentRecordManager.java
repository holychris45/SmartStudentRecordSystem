package com.ssrmt.services;

import com.ssrmt.modules.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRecordManager {
    // Collection implementation
    private final List<Student> studentList;

    public StudentRecordManager() {
        this.studentList = new ArrayList<>();
    }

    public void addStudent(Student student) throws Exception {
        // Validation for duplicate records
        for (Student s : studentList) {
            if (s.getId().equalsIgnoreCase(student.getId())) {
                throw new Exception("A student with ID " + student.getId() + " already exists.");
            }
        }
        studentList.add(student);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(studentList); // Returns a defensive copy
    }

    public boolean deleteStudent(String id) {
        return studentList.removeIf(student -> student.getId().equalsIgnoreCase(id));
    }

    public Optional<Student> findStudentById(String id) {
        return studentList.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst();
    }
}