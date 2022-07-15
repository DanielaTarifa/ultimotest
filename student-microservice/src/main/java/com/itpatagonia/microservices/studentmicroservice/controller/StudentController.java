package com.itpatagonia.microservices.studentmicroservice.controller;

import com.itpatagonia.microservices.studentmicroservice.Exceptions.NoEntityException;
import com.itpatagonia.microservices.studentmicroservice.model.Subject;
import com.itpatagonia.microservices.studentmicroservice.model.Student;
import com.itpatagonia.microservices.studentmicroservice.model.Subject;
import com.itpatagonia.microservices.studentmicroservice.service.StudentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student){
        Student studentNew = studentService.createStudent(student);
        return new ResponseEntity<Student>(studentNew,HttpStatus.CREATED);
    }

    @CircuitBreaker(name = "subjectsCB", fallbackMethod = "fallBackSaveSubject")
    @PostMapping(value ="/createsubject/{studentId}")
    public ResponseEntity<Subject> createExam(@PathVariable("studentId") Long studentId , @RequestBody Subject subject){
        subject.setStudentId(studentId);
        return ResponseEntity.ok().body(studentService.createSubject(subject));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getStudents(){
        return ResponseEntity.ok(studentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {

        try {
            Student student = studentService.findById(id);
            return ResponseEntity.ok(student);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Student No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.badRequest().body( HttpStatus.BAD_REQUEST + "Student No encontrado ");
        }
    }

    @CircuitBreaker(name = "subjectsCB", fallbackMethod = "fallBackGetSubjects")
    @GetMapping("/subjectbystudent/{id}")
    public ResponseEntity<Map<String,Object>> getSubjects(@PathVariable("id") Long studentId){
        List<Subject> subject = studentService.getSubjects(studentId);
        Map<String,Object> studentMap = new HashMap<>();
        if ( studentMap.isEmpty()){
            studentMap.put("El estudiante " + studentId , " No tiene Materias " );
        }
        studentMap.put("El estudiante " + studentId , studentMap);
        return ResponseEntity.ok().body(studentMap);
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student){
        Student studentNew = new Student();
        try {
            studentNew = studentService.updateStudent(student);
            return ResponseEntity.ok(studentNew);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Student No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.status(400).body(studentNew);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") Long id){
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok("Student Eliminado");
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Student No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.badRequest().body( HttpStatus.BAD_REQUEST + "Student No Eliminado");
        }
    }

    private ResponseEntity<List<Subject>> fallBackGetSubjects(@PathVariable("studentId") Long studentId, RuntimeException e) {
        return new ResponseEntity("El Estudiante " + studentId + " todavia no tiene materias(CB)", HttpStatus.OK);
    }

    private ResponseEntity<Subject> fallBackSaveSubjects(@PathVariable("studentId") Long studentId , @RequestBody Subject subject, RuntimeException e) {
        return new ResponseEntity("El Estudiante " + studentId + " no puede tener materia()CB", HttpStatus.OK);
    }
}
