package com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.domainobject.Subject;

/**
 * Student is a simple POJO that is used to store grade information and pass it around in the service layer -> Grade Domain Object
 *      - so that all the implications of the hibernate entity class (com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity) are not carried all throughout the service layer
 *
 * @author Ispas Mihai
 */
public class Grade {
    private int gradeId;
    private Student student;
    private Subject subject;
    private float grade;

    public Grade(int gradeId, Student student, Subject subject, float grade) {
        this.gradeId = gradeId;
        this.student = student;
        this.subject = subject;
        this.grade = grade;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    /**
     * <p>
     *     This is a utility method to map a grade hibernate entity to a grade domain object
     * </p>
     * @param gradeEntity -> the grade entity to be mapped to a grade domain object
     * @return a grade domain object that was mapped from the incoming grade entity
     */
    public static Grade ofEntity(final GradeEntity gradeEntity)
    {
        return new Grade(
                gradeEntity.getGradeId(),
                Student.ofEntity(gradeEntity.getStudent()),
                Subject.ofEntity(gradeEntity.getSubject()),
                gradeEntity.getGrade());
    }

    /**
     * <p>
     *     This is a utility method to map a grade domain object to a grade hibernate entity
     * </p>
     * @param grade -> the grade domain object to be mapped to a hibernate entity
     * @return a grade hibernate entity mapped from the incoming grade domain object
     */
    public static GradeEntity toEntity(final Grade grade)
    {
        final GradeEntity gradeEntity = new GradeEntity();
        gradeEntity.setGradeId(grade.getGradeId());
        gradeEntity.setGrade(grade.getGrade());
        gradeEntity.setStudent(Student.toEntity(grade.getStudent()));
        gradeEntity.setSubject(Subject.toEntity(grade.getSubject()));
        return gradeEntity;
    }
}
