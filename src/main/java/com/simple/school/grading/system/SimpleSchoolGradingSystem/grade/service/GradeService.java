package com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.service;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;

import java.util.List;

public interface GradeService
{
    List<Grade> getAll();
    List<Grade> getStudentGrades(final int studentId);
    List<Grade> getSubjectGrades(final int subjectId);
    void save(final int studentId, final int subjectId, final Grade grade);
    void update(final Grade grade);
    void delete(final Grade grade);
}
