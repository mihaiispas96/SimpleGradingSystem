package com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity;

import javax.persistence.*;
import java.util.List;

/**
 * StudentEntity it's a java class used for interfacing the student database entity as a Java object managed by hibernate
 *      - it has the @Entity annotation so that hibernate know that it needs to manage it
 *      - it has the @Table annotation to specify the name of the database table that this hibernate entity will be associated with -> Student table
 *
 * @author Ispas Mihai
 */
@Entity
@Table(name = "Student")
public class StudentEntity {
    /**
     * The student id
     *      - it has the @Id annotation so that hibernate knows this is an identifier member -> it will be associated with the primary key in the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;
    private String firstName;
    private String lastName;

    /**
     * The list of all the grade entities associated with a particular student entity
     *      - it has a @OneToMany annotation which is telling hibernate that there is a one-to-many relationship between the Student entity and the Grade entity -> a student can have more grades
     *      - the mappedBy is telling hibernate that the link between these 2 entities (Student and Grade) was already done within the Grade entity, using the member called "student"
     */
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    private List<GradeEntity> grades;

    public StudentEntity() {
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

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }
}
