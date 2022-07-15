package com.itpatagonia.microservices.subjectmicroservice.service;

import com.itpatagonia.microservices.subjectmicroservice.Exceptions.NoEntityException;
import com.itpatagonia.microservices.subjectmicroservice.model.Subject;
import com.itpatagonia.microservices.subjectmicroservice.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public Subject createExam(Subject subject) {
        return subjectRepository.save(subject);
    }

    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    public Subject findById(Long id) throws NoEntityException {
        return subjectRepository.findById(id).orElseThrow(() -> new NoEntityException("No existe Subject con " + id));
    }

    public List<Subject> finByStudentId(Long studentId){
        return subjectRepository.findByStudentId(studentId);
    }

    public Subject updateSubject(Subject subject) throws NoEntityException {
        Subject subjectOld = subjectRepository.findById(subject.getId()).orElseThrow(
                () -> new NoEntityException("No existe Exam con " + subject.getId()));
        //examOld.setName(exam.getName());
        return subjectRepository.save(subjectOld);
    }

    public void deleteSubject(Long id) throws NoEntityException {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new NoEntityException("No existe Subject con " + id));
        subjectRepository.delete(subject);
    }

}
