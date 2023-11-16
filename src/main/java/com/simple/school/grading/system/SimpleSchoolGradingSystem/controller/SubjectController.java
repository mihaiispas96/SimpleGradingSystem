package com.simple.school.grading.system.SimpleSchoolGradingSystem.controller;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.service.GradeService;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.service.StudentService;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.domainobject.Subject;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.subject.service.SubjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController os a REST controller accepting requests to the root path /subject
 *
 * @author Ispas Mihai
 */
@RestController
@RequestMapping(value = "/subject")
public class SubjectController {
    private final SubjectService subjectService;
    private final GradeService gradeService;

    public SubjectController(final SubjectService subjectService, final GradeService gradeService)
    {
        this.subjectService = subjectService;
        this.gradeService = gradeService;
    }

    /**
     * <p>
     *     This method serves requests to get all subjects -> request with GET method to the root path /subject
     * </p>
     * @return a list of all subjects
     */
    @GetMapping(produces = "application/json")
    public List<Subject> getAll()
    {
        return subjectService.getAll();
    }

    /**
     * <p>
     *     This method serves requests to get one subject -> request with GET method to the path /subject/{subjectId}
     * </p>
     * @param id -> the id of the subject to fetch
     * @return a subject for a particular id
     */
    @GetMapping(path = "/{id}", produces = "application/json")
    public Subject getById(@PathVariable("id") final int id)
    {
        return subjectService.getById(id);
    }

    /**
     * <p>
     *     This method serves requests to get the grades associated to a particular subject -> request with GET method to the path /subject/{subjectId}/grades
     * </p>
     * @param id -> the id of the subject for witch to fetch the grades
     * @return a list of all the grades associated to a particular subject
     */
    @GetMapping(path = "/{id}/grades", produces = "application/json")
    public List<Grade> getSubjectGrades(@PathVariable("id") final int id)
    {
        return gradeService.getSubjectGrades(id);
    }

    /**
     * <p>
     *     This method serves requests to save a new subject -> request with POST method to the path root path /subject
     * </p>
     * @param subject -> the new subject object to be saved
     */
    @PostMapping
    public void save(@RequestBody final Subject subject)
    {
        subjectService.save(subject);
    }

    /**
     * <p>
     *     This method serves requests to update an existing subject -> request with PUT method to the path /subject/{subjectId}
     * </p>
     * @param id -> the id of the subject that will be updated
     * @param subject -> the subject object with the updated fields
     */
    @PutMapping(path = "/{id}")
    public void update(
            @PathVariable("id") final int id,
            @RequestBody final Subject subject)
    {
        subjectService.update(id, subject);
    }

    /**
     * <p>
     *     This method serves requests to delete an existing subject -> request with DELETE method to the path /subject/{subjectId}
     * </p>
     * @param id -> the id of the subject that will be deleted
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") final int id)
    {
        subjectService.delete(id);
    }
}
