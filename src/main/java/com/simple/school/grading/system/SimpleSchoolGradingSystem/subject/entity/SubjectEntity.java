package com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity;

import javax.persistence.*;
import java.util.List;

/**
 * SubjectEntity it's a java class used for interfacing the subject database entity as a Java object managed by hibernate
 *      - it has the @Entity annotation so that hibernate know that it needs to manage it
 *      - it has the @Table annotation to specify the name of the database table that this hibernate entity will be associated with -> Subject table
 *
 * @author Ispas Mihai
 */
@Entity
@Table(name = "Subject")
public class SubjectEntity {
    /**
     * The subject id
     *      - it has the @Id annotation so that hibernate knows this is an identifier member -> it will be associated with the primary key in the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subjectId;
    private String name;

    /**
     * The list of all the grade entities associated with a particular subject entity
     *      - it has a @OneToMany annotation which is telling hibernate that there is a one-to-many relationship between the Subject entity and the Grade entity -> a subject can have more associated grades
     *      - the mappedBy is telling hibernate that the link between these 2 entities (Subject and Grade) was already done within the grade entity, using the member called "subject"
     */
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
    private List<GradeEntity> grades;

    public SubjectEntity() {
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

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }
}
