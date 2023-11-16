package com.simple.school.grading.system.SimpleSchoolGradingSystem.student.service.impl;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.GradeRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.session.SessionContext;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.StudentRepository;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.entity.StudentEntity;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.service.StudentService;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    public StudentServiceImpl(
            final StudentRepository studentRepository,
            final GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Student> getAll() {
        return studentRepository.getAll()
                .stream()
                .map(Student::ofEntity)
                .collect(Collectors.toList());
    }

    public Student getById(final int studentId) {
        return Student.ofEntity(studentRepository.getById(studentId));
    }

    public void save(final Student student) {
        studentRepository.save(Student.toEntity(student));
    }

    public void update(final int id, final Student student) {
        Transaction transaction = null;
        try {
            transaction = SessionContext.getInstance().getCurrentSession().beginTransaction();

            final StudentEntity studentEntity = studentRepository.getById(id);
            if (studentEntity == null)
            {
                throw new RuntimeException("There is no student with id=" + id + " to be updated.");
            }

            syncStudentDomainObjectToEntity(student, studentEntity);

            studentRepository.merge(studentEntity);

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

            final StudentEntity studentEntity = studentRepository.getById(id);
            if (studentEntity == null)
            {
                throw new RuntimeException("There is no student with id=" + id + " to be deleted.");
            }

            studentRepository.merge(studentEntity);
            studentRepository.remove(studentEntity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
            {
                transaction.rollback();
                throw e;
            }
        }
    }

    private static void syncStudentDomainObjectToEntity(final Student studentDomainObject, final StudentEntity studentEntity) {
        if (studentDomainObject.getFirstName() != null && !studentDomainObject.getFirstName().isEmpty()) {
            studentEntity.setFirstName(studentDomainObject.getFirstName());
        }
        if (studentDomainObject.getLastName() != null && !studentDomainObject.getLastName().isEmpty()) {
            studentEntity.setLastName(studentDomainObject.getLastName());
        }
        if (studentDomainObject.getGrades() != null && !studentDomainObject.getGrades().isEmpty()) {
            studentEntity.setGrades(studentDomainObject.getGrades().stream()
                    .map(Grade::toEntity)
                    .collect(Collectors.toList()));

        }
    }
}
