package com.simple.school.grading.system.SimpleSchoolGradingSystem.grade;


import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * GradeRepository is a Repository (Data Access Object -> DAO) class that uses hibernate capabilities to communicate with the Grade database table
 *      - it hase the @Repository so that spring knows to manage this class as a bean of type repository
 *
 * @author Ispas Mihai
 */
@Repository
public class GradeRepository {
    /**
     * <p>
     *     This is a repository method to get all the grades from the database
     * </p>
     * @return a list of all the grades in the database -> using GradeEntity object that is managed by hibernate
     */
    public List<GradeEntity> getAll()
    {
        return SessionContext.getInstance().getCurrentSession()
                .createQuery("from GradeEntity", GradeEntity.class)
                .list();
    }

    public List<GradeEntity> getByStudentId(final int studentId)
    {
        return SessionContext.getInstance().getCurrentSession()
                .createQuery("from GradeEntity as gradeEntity where gradeEntity.student.studentId ='" + studentId + "'", GradeEntity.class)
                .list();
    }

    public List<GradeEntity> getBySubjectId(final int subjectId)
    {
        return SessionContext.getInstance().getCurrentSession()
                .createQuery("from GradeEntity as gradeEntity where gradeEntity.subject.subjectId ='" + subjectId + "'", GradeEntity.class)
                .list();
    }

    public List<GradeEntity> getByStudentAndSubjectId(final int studentId, final int subjectId)
    {
        return SessionContext.getInstance().getCurrentSession()
                .createQuery("from GradeEntity as gradeEntity where gradeEntity.student.studentId ='" + studentId + "' and gradeEntity.subject.subjectId ='" + subjectId + "'", GradeEntity.class)
                .list();
    }

    /**
     * <p>
     *     This is a repository method to persist an incoming grade in the database
     * </p>
     * @param grade -> the grade entity to be persisted in the database -> using GradeEntity object that is managed by hibernate
     */
    public void save(final GradeEntity grade)
    {
        SessionContext.getInstance().getCurrentSession()
                .save(grade);
    }

    /**
     * <p>
     *     This is a repository method to update an existing grade in the database using the incoming grade entity
     *          - merge method from hibernate is used because it also reattaches the entity object to the hibernate context in case it was previously detached
     * </p>
     * @param grade -> the grade entity to be updated in the database -> using GradeEntity object that is managed by hibernate
     */
    public void merge(final GradeEntity grade)
    {
        SessionContext.getInstance().getCurrentSession()
                .merge(grade);
    }

    /**
     * <p>
     *     This is a repository method to delete the incoming grade from the database
     * </p>
     * @param grade -> the grade entity to be removed from the database -> using GradeEntity object that is managed by hibernate
     */
    public void remove(final GradeEntity grade)
    {
        SessionContext.getInstance().getCurrentSession()
                .remove(grade);
    }
}
