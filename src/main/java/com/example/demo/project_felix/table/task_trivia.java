package com.example.demo.project_felix.table;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

// bank soalnya
public class task_trivia {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idtemplate;

    @Column(columnDefinition = "LONGTEXT")
    private String deskripsi;

    @Column(length = 50)
    private String option1;
    @Column(length = 50)
    private String option2;
    @Column(length = 50)
    private String option3;
    @Column(length = 50)
    private String option4;

    @Column(length = 50)
    private Integer answer;

    public task_trivia() {

    }

}