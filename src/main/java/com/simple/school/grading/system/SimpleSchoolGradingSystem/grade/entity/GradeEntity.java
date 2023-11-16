package com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity.StudentEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity.SubjectEntity;

import javax.persistence.*;

/**
 * GradeEntity it's a java class used for interfacing the grade database entity as a Java object managed by hibernate
 *      - it has the @Entity annotation so that hibernate know that it needs to manage it
 *      - it has the @Table annotation to specify the name of the database table that this hibernate entity will be associated with -> Grade table
 *
 * @author Ispas Mihai
 */
@Entity
@Table(name = "Grade")
public class GradeEntity {
    /**
     * The grade id
     *      - it has the @Id annotation so that hibernate knows this is an identifier member -> it will be associated with the primary key in the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int gradeId;
    /**
     * The student of a grade
     *      - it has the @ManyToOne annotation so that hibernate knows there is a many-to-one relationship between Grade entity and Student entity -> a grade has a student associated
     *      - it has the @JoinColumn annotation which specifier which column from the Student table will be used as foreign key in the Grade Table -> makes the actual link between the two tables (in this case and usually, it's the primary key)
     */
    @ManyToOne
    @JoinColumn(name="student_id", nullable=false)
    private StudentEntity student;
    /**
     * The student of a grade
     *      - it has the @ManyToOne annotation so that hibernate knows there is a many-to-one relationship between Grade entity and Subject entity -> a grade has a subject associated
     *      - it has the @JoinColumn annotation which specifier which column from the Subject table will be used as foreign key in the Grade Table -> makes the actual link between the two tables (in this case and usually, it's the primary key)
     */
    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private SubjectEntity subject;
    /**
     * The value of the actual grade
     */
    private float grade;

    public GradeEntity() {
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }
}
