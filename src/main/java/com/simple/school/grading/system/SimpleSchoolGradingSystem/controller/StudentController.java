package com.simple.school.grading.system.SimpleSchoolGradingSystem.controller;

import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.domainobject.Grade;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.grade.service.GradeService;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.domainobject.Student;
import com.simple.school.grading.system.SimpleSchoolGradingSystem.student.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * StudentController os a REST controller accepting requests to the root path /student
 *
 * @author Ispas Mihai
 */
@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private final StudentService studentService;
    private final GradeService gradeService;

    public StudentController(final StudentService studentService, final GradeService gradeService)
    {
        this.studentService = studentService;
        this.gradeService = gradeService;
    }

    /**
     * <p>
     *     This method serves requests to get all students -> request with GET method to the root path /student
     * </p>
     * @return a list of all students
     */
    @GetMapping(produces = "application/json")
    public List<Student> getAll()
    {
        return studentService.getAll();
    }

    /**
     * <p>
     *     This method serves requests to get one student -> request with GET method to the path /student/{studentId}
     * </p>
     * @param id -> the id of the student to fetch
     * @return a student for a particular id
     */
    @GetMapping(path = "/{studentId}", produces = "application/json")
    public Student getById(@PathVariable("studentId") final int id)
    {
        return studentService.getById(id);
    }

    /**
     * <p>
     *     This method serves requests to get the grades of a student -> request with GET method to the path /student/{studentId}/grades
     * </p>
     * @param id -> the id of the student for witch to fetch the grades
     * @return a list of all the grades of a student
     */
    @GetMapping(path = "/{studentId}/grades", produces = "application/json")
    public List<Grade> getStudentGrades(@PathVariable("studentId") final int id)
    {
        return gradeService.getStudentGrades(id);
    }

    /**
     * <p>
     *     This method serves requests to save a new student -> request with POST method to the path root path /student
     * </p>
     * @param student -> the new student object to be saved
     */
    @PostMapping(consumes = "application/json")
    public void save(@RequestBody final Student student)
    {
        studentService.save(student);
    }

    /**
     * <p>
     *     This method serves requests to update an existing student -> request with PUT method to the path /student/{studentId}
     * </p>
     * @param id -> the id of the student that will be updated
     * @param student -> the student object with the updated fields
     */
    @PutMapping(path = "/{studentId}", consumes = "application/json", produces = "application/json")
    public void update(
            @PathVariable("studentId") final int id,
            @RequestBody final Student student)
    {
        studentService.update(id, student);
    }

    /**
     * <p>
     *     This method serves requests to delete an existing student -> request with DELETE method to the path /student/{studentId}
     * </p>
     * @param id -> the id of the student that will be deleted
     */
    @DeleteMapping(path = "/{studentId}")
    public void delete(@PathVariable("studentId") final int id)
    {
        studentService.delete(id);
    }

    /**
     * <p>
     *     This method serves requests to save a new grade for a student, at a particular subject -> request with POST method to the path /student/{studentId}/grades/{subjectId}
     * </p>
     * @param grade -> the new student object to be saved
     */
    @PostMapping(path = "/{studentId}/grades/{subjectId}", consumes = "application/json")
    public void saveStudentGrade(
            @PathVariable("studentId") final int studentId,
            @PathVariable("subjectId") final int subjectId,
            @RequestBody final Grade grade)
    {
        gradeService.save(studentId, subjectId, grade);
    }
}
