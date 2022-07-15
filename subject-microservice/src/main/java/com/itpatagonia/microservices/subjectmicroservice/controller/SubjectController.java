package com.itpatagonia.microservices.subjectmicroservice.controller;

import com.itpatagonia.microservices.subjectmicroservice.Exceptions.NoEntityException;
import com.itpatagonia.microservices.subjectmicroservice.model.Subject;
import com.itpatagonia.microservices.subjectmicroservice.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<Subject> createExam(@RequestBody Subject exam){
        Subject examNew = subjectService.createExam(exam);
        return new ResponseEntity<Subject>(examNew,HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Subject>> getSubjects(){
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubjectById(@PathVariable("id") Long id) {

        try {
            Subject exam = subjectService.findById(id);
            return ResponseEntity.ok(exam);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Subject No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.badRequest().body( HttpStatus.BAD_REQUEST + "Subject No encontrado ");
        }
    }

    @GetMapping("/bystudent/{studentid}")
    public ResponseEntity<?> finByStudentId(@PathVariable("studentid") Long studentId){
        return ResponseEntity.ok().body(subjectService.finByStudentId(studentId));
    }

    @PutMapping
    public ResponseEntity<Subject> updateSubject(@RequestBody Subject subject){
        Subject subjectNew = new Subject();
        try {
            subjectNew = subjectService.updateSubject(subject);
            return ResponseEntity.ok(subjectNew);
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Subject No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.status(400).body(subjectNew);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable("id") Long id){
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok("Exam Eliminado");
        } catch (NoEntityException e) {
            System.out.println(e.getMessage());
            //return new ResponseEntity<>("Subject No encontrado", HttpStatusCode.valueOf(400));
            return ResponseEntity.badRequest().body( HttpStatus.BAD_REQUEST + "Subject No Eliminado");
        }
    }

}
