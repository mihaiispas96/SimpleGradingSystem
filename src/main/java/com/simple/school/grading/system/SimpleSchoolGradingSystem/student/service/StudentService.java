package com.simple.school.grading.system.SimpleSchoolGradingSystem.student.service;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;

import java.util.List;

public interface StudentService
{
    List<Student> getAll();
    Student getById(final int studentId);
    void save(final Student student);
    void update(final int id, final Student student);
    void delete(final int id);
}
