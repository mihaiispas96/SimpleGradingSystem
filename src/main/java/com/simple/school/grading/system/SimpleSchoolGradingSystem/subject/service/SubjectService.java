package com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.service;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.domainobject.Subject;

import java.util.List;

public interface SubjectService
{
    List<Subject> getAll();
    Subject getById(final int subjectId);
    void save(final Subject subject);
    void update(final int id, final Subject subject);
    void delete(final int id);
}
