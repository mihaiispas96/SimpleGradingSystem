package com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.service.impl;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.GradeRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.entity.GradeEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.service.GradeService;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.StudentRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity.StudentEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.SubjectRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.domainobject.Subject;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.entity.SubjectEntity;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public GradeServiceImpl(final GradeRepository gradeRepository, final StudentRepository studentRepository, final SubjectRepository subjectRepository)
    {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    public List<Grade> getAll() {
        return gradeRepository.getAll()
                .stream()
                .map(Grade::ofEntity)
                .collect(Collectors.toList());
    }

    public List<Grade> getStudentGrades(final int studentId)
    {
        Transaction transaction = null;
        List<GradeEntity> gradeEntities = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final StudentEntity studentEntity = studentRepository.getById(studentId);
            if (studentEntity == null)
            {
                throw new RuntimeException("There is no student with id=" + studentId + " for which to fetch grades.");
            }

            gradeEntities = gradeRepository.getByStudentId(studentEntity.getStudentId());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
        return Optional.ofNullable(gradeEntities)
                .map(List::stream)
                .map(gradeEntityStream -> gradeEntityStream.map(Grade::ofEntity))
                .map(gradeStream -> gradeStream.collect(Collectors.toList()))
                .orElse(null);
    }

    public List<Grade> getSubjectGrades(final int subjectId)
    {
        Transaction transaction = null;
        List<GradeEntity> gradeEntities = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final SubjectEntity subjectEntity = subjectRepository.getById(subjectId);
            if (subjectEntity == null)
            {
                throw new RuntimeException("There is no subject with id=" + subjectId + " for which to fetch grades.");
            }

            gradeEntities = gradeRepository.getBySubjectId(subjectEntity.getSubjectId());

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
        return Optional.ofNullable(gradeEntities)
                .map(List::stream)
                .map(gradeEntityStream -> gradeEntityStream.map(Grade::ofEntity))
                .map(gradeStream -> gradeStream.collect(Collectors.toList()))
                .orElse(null);
    }

    public void save(final int studentId, final int subjectId, final Grade grade)
    {
        Transaction transaction = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final StudentEntity studentEntity = studentRepository.getById(studentId);
            if (studentEntity == null)
            {
                throw new RuntimeException("There is no student with id=" + studentId + " for which to store a new grade.");
            }

            final SubjectEntity subjectEntity = subjectRepository.getById(subjectId);
            if (subjectEntity == null)
            {
                throw new RuntimeException("There is no subject with id=" + subjectId + " for which to store a new grade.");
            }

            grade.setStudent(Student.ofEntity(studentEntity));
            grade.setSubject(Subject.ofEntity(subjectEntity));

            gradeRepository.save(Grade.toEntity(grade));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
    }

    public void update(final int studentId, final int subjectId, final Grade grade) {
        gradeRepository.merge(Grade.toEntity(grade));

        Transaction transaction = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final StudentEntity studentEntity = studentRepository.getById(studentId);
            if (studentEntity == null)
            {
                throw new RuntimeException("There is no student with id=" + studentId + " for which to store a new grade.");
            }

            final SubjectEntity subjectEntity = subjectRepository.getById(subjectId);
            if (subjectEntity == null)
            {
                throw new RuntimeException("There is no subject with id=" + subjectId + " for which to store a new grade.");
            }

            final List<GradeEntity> gradeEntities = gradeRepository.getByStudentAndSubjectId(studentId, subjectId);
            if (gradeEntities == null || gradeEntities.isEmpty())
            {
                throw new RuntimeException("There is no subject with id=" + subjectId + " for which to store a new grade.");
            }

            grade.setStudent(Student.ofEntity(studentEntity));
            grade.setSubject(Subject.ofEntity(subjectEntity));

            gradeRepository.save(Grade.toEntity(grade));

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
    }

    public void delete(final Grade grade) {
        gradeRepository.remove(Grade.toEntity(grade));
    }
}
