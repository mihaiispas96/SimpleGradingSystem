package com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity.StudentEntity;

import java.util.List;

/**
 * Student is a simple POJO that is used to store student information and pass it around in the service layer -> Student Domain Object
 *      - so that all the implications of the hibernate entity class (com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity.StudentEntity) are not carried all throughout the service layer
 *
 * @author Ispas Mihai
 */
public class Student {
    private int studentId;
    private String firstName;
    private String lastName;
    private List<Grade> grades;

    public Student(final int studentId, final String firstName, final String lastName) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    /**
     * <p>
     *     This is a utility method to map a student hibernate entity to a student domain object
     * </p>
     * @param studentEntity -> the student entity to be mapped to a student domain object
     * @return a student domain object that was mapped from the incoming student entity
     */
    public static Student ofEntity(final StudentEntity studentEntity)
    {
        return new Student(
                studentEntity.getStudentId(),
                studentEntity.getFirstName(),
                studentEntity.getLastName());
    }

    /**
     * <p>
     *     This is a utility method to map a student domain object to a student hibernate entity
     * </p>
     * @param student -> the student domain object to be mapped to a hibernate entity
     * @return a student hibernate entity mapped from the incoming student domain object
     */
    public static StudentEntity toEntity(final Student student)
    {
        final StudentEntity studentEntity = new StudentEntity();
        studentEntity.setStudentId(student.getStudentId());
        studentEntity.setFirstName(student.getFirstName());
        studentEntity.setLastName(student.getLastName());
        return studentEntity;
    }
}
