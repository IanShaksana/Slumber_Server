package com.example.demo.project_felix.table;

import java.util.Date;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class task_trivia_user {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idtrivia;
    @Column(length = 50)
    private String idtask;
    @Column(length = 50)
    private String idprofile;

    @Column(columnDefinition = "DATE")
    private Date tanggal;

    public task_trivia_user() {

    }

    public task_trivia_user(task_trivia trivia, task_user task, profile profile) {
        setIdprofile(profile.getId());
        setIdtrivia(trivia.getId());
        setIdtask(task.getId());
        setTanggal(new Date());
    }

}