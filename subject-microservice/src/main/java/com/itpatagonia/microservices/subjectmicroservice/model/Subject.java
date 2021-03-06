package com.itpatagonia.microservices.subjectmicroservice.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exam")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "examendate")
    private LocalDate examenDate;

    @Column(name = "score")
    private Float score;

    @Column(name = "studentid")
    private Long studentId;

}