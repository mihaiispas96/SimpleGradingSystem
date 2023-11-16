package com.simple.school.grading.system.SimpleSchoolGradingSystem.subject;


import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity.SubjectEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SubjectRepository is a Repository (Data Access Object -> DAO) class that uses hibernate capabilities to communicate with the Subject database table
 *      - it hase the @Repository so that spring knows to manage this class as a bean of type repository
 *
 * @author Ispas Mihai
 */
@Repository
public class SubjectRepository {
    /**
     * <p>
     *     This is a repository method to get all the subjects from the database
     * </p>
     * @return a list of all the subjects in the database -> using SubjectEntity object that is managed by hibernate
     */
    public List<SubjectEntity> getAll() {
        return SessionContext.getInstance().getCurrentSession()
                .createQuery("from SubjectEntity", SubjectEntity.class)
                .list();
    }

    /**
     * <p>
     *     This is a repository method to get a particular subject from the database using an id
     * </p>
     * @param subjectId -> the id of the subject that is attempted to be retrieved
     * @return the subject that was found in the database for the incoming id -> using SubjectEntity object that is managed by hibernate
     */
    public SubjectEntity getById(final int subjectId) {
        return SessionContext.getInstance().getCurrentSession()
                .get(SubjectEntity.class, subjectId);
    }

    /**
     * <p>
     *     This is a repository method to persist an incoming subject in the database
     * </p>
     * @param subject -> the subject entity to be persisted in the database -> using SubjectEntity object that is managed by hibernate
     */
    public void save(final SubjectEntity subject) {
        SessionContext.getInstance().getCurrentSession()
                .save(subject);
    }

    /**
     * <p>
     *     This is a repository method to update an existing subject in the database using the incoming subject entity
     *          - merge method from hibernate is used because it also reattaches the entity object to the hibernate context in case it was previously detached
     * </p>
     * @param subject -> the subject entity to be updated in the database -> using SubjectEntity object that is managed by hibernate
     */
    public void merge(final SubjectEntity subject) {
        SessionContext.getInstance().getCurrentSession()
                .merge(subject);
    }

    /**
     * <p>
     *     This is a repository method to delete the incoming subject from the database
     * </p>
     * @param subject -> the subject entity to be removed from the database -> using SubjectEntity object that is managed by hibernate
     */
    public void remove(final SubjectEntity subject) {
        SessionContext.getInstance().getCurrentSession()
                .remove(subject);
    }
}
