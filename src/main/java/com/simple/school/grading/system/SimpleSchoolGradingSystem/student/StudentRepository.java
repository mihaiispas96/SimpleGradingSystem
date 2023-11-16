package com.simple.school.grading.system.SimpleSchoolGradingSystem.student;


import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * StudentRepository is a Repository (Data Access Object -> DAO) class that uses hibernate capabilities to communicate with the Student database table
 *      - it hase the @Repository so that spring knows to manage this class as a bean of type repository
 *
 * @author Ispas Mihai
 */
@Repository
public class StudentRepository {
    /**
     * <p>
     *     This is a repository method to get all the students from the database
     * </p>
     * @return a list of all the students in the database -> using StudentEntity object that is managed by hibernate
     */
    public List<StudentEntity> getAll() {
        return SessionContext.getInstance().getCurrentSession()
                .createQuery("from StudentEntity", StudentEntity.class)
                .list();
    }

    /**
     * <p>
     *     This is a repository method to get a particular student from the database using an id
     * </p>
     * @param studentId -> the id of the student that is attempted to be retrieved
     * @return the student that was found in the database for the incoming id -> using StudentEntity object that is managed by hibernate
     */
    public StudentEntity getById(final int studentId) {
        return SessionContext.getInstance().getCurrentSession()
                .get(StudentEntity.class, studentId);
    }

    /**
     * <p>
     *     This is a repository method to persist an incoming student in the database
     * </p>
     * @param student -> the student entity to be persisted in the database -> using StudentEntity object that is managed by hibernate
     */
    public void save(final StudentEntity student) {
        SessionContext.getInstance().getCurrentSession()
                .save(student);
    }

    /**
     * <p>
     *     This is a repository method to update an existing student in the database using the incoming student entity
     *          - merge method from hibernate is used because it also reattaches the entity object to the hibernate context in case it was previously detached
     * </p>
     * @param student -> the student entity to be updated in the database -> using StudentEntity object that is managed by hibernate
     */
    public void merge(final StudentEntity student) {
        SessionContext.getInstance().getCurrentSession()
                .merge(student);
    }

    /**
     * <p>
     *     This is a repository method to delete the incoming student from the database
     * </p>
     * @param student -> the student entity to be removed from the database -> using StudentEntity object that is managed by hibernate
     */
    public void remove(final StudentEntity student) {
        SessionContext.getInstance().getCurrentSession()
                .remove(student);
    }
}
