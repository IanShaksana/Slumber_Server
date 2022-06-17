package com.example.demo.project_felix.table;

import java.util.Date;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class task_article {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idtask;
    @Column(length = 50)
    private String idarticle;
    
    @Column(columnDefinition = "DATE")
    private Date tanggal;
    

    public task_article() {

    }

    public task_article(String idarticle, task_user task) {
        setIdarticle(idarticle);
        setIdtask(task.getId());
        setTanggal(new Date());
    }

    

}