package com.itpatagonia.microservices.studentmicroservice.feignclients;

import com.itpatagonia.microservices.studentmicroservice.model.Subject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name="subject-microservice", url = "http://localhost:8002/subject")
@FeignClient(name="subject-microservice")
public interface SubjectFeignClient {
    @PostMapping("/subjects")
    public Subject createSubject(@RequestBody Subject subject);

    @GetMapping("/subjects/bystudent/{studentid}")
    public ResponseEntity<?> finByStudentId(@PathVariable("studentid") Long studentId);

}
