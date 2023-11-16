package com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.service.impl;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.GradeRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.SubjectRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.domainobject.Subject;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity.SubjectEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.service.SubjectService;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final GradeRepository gradeRepository;

    public SubjectServiceImpl(
            final SubjectRepository subjectRepository,
            final GradeRepository gradeRepository) {
        this.subjectRepository = subjectRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Subject> getAll() {
        return subjectRepository.getAll()
                .stream()
                .map(Subject::ofEntity)
                .collect(Collectors.toList());
    }

    public Subject getById(final int subjectId) {
        return Subject.ofEntity(subjectRepository.getById(subjectId));
    }

    public void save(final Subject subject) {
        subjectRepository.save(Subject.toEntity(subject));
    }

    public void update(final int id, final Subject subject) {
        Transaction transaction = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final SubjectEntity subjectEntity = subjectRepository.getById(id);
            if (subjectEntity == null)
            {
                throw new RuntimeException("There is no subject with id=" + id + " to be updated.");
            }

            syncSubjectEntityWithSubjectDomainObject(subject, subjectEntity);

            subjectRepository.merge(subjectEntity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
    }

    public void delete(final int id) {
        Transaction transaction = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final SubjectEntity subjectEntity = subjectRepository.getById(id);
            if (subjectEntity == null)
            {
                throw new RuntimeException("There is no subject with id=" + id + " to be deleted.");
            }

            subjectRepository.merge(subjectEntity);
            subjectRepository.remove(subjectEntity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
    }

    private static void syncSubjectEntityWithSubjectDomainObject(final Subject subjectDomainObject, final SubjectEntity subjectEntity)
    {
        if (subjectDomainObject.getName() != null && !subjectDomainObject.getName().isEmpty()) {
            subjectEntity.setName(subjectDomainObject.getName());
        }
        if (subjectDomainObject.getGrades() != null && !subjectDomainObject.getGrades().isEmpty()) {
            subjectEntity.setGrades(subjectDomainObject.getGrades().stream()
                    .map(Grade::toEntity)
                    .collect(Collectors.toList()));
        }
    }
}
