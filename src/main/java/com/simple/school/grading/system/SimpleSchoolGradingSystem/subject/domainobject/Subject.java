package com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.domainobject;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity.SubjectEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Subject is a simple POJO that is used to store subject information and pass it around in the service layer -> Subject Domain Object
 *      - so that all the implications of the hibernate entity class (com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity.SubjectEntity) are not carried all throughout the service layer
 *
 * @author Ispas Mihai
 */
public class Subject {
    private int subjectId;
    private String name;
    private List<Grade> grades;

    public Subject(int subjectId, String name) {
        this.subjectId = subjectId;
        this.name = name;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    /**
     * <p>
     *     This is a utility method to map a subject hibernate entity to a subject domain object
     * </p>
     * @param subjectEntity -> the subject entity to be mapped to a subject domain object
     * @return a subject domain object that was mapped from the incoming subject entity
     */
    public static Subject ofEntity(final SubjectEntity subjectEntity)
    {
        return new Subject(
                subjectEntity.getSubjectId(),
                subjectEntity.getName());
    }

    /**
     * <p>
     *     This is a utility method to map a subject domain object to a subject hibernate entity
     * </p>
     * @param subject -> the subject domain object to be mapped to a hibernate entity
     * @return a subject hibernate entity mapped from the incoming subject domain object
     */
    public static SubjectEntity toEntity(final Subject subject)
    {
         final SubjectEntity subjectEntity = new SubjectEntity();
         subjectEntity.setSubjectId(subject.getSubjectId());
         subjectEntity.setName(subject.getName());
         return subjectEntity;
    }
}
