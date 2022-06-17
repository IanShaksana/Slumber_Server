package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class task_music {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idtask;

    @Column(columnDefinition = "DATE")
    private Date tanggal;

    public task_music() {

    }

    public task_music(task_user task) {
        setIdtask(task.getId());
        setTanggal(new Date());
    }

}