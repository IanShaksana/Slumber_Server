package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class task_alarm {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idtask;

    @Column(length = 50)
    private Integer counter = 0;
    
    @Column(columnDefinition = "DATE")
    private Date tanggal;
    

    public task_alarm() {

    }

    public task_alarm(task_user task) {
        setIdtask(task.getId());
    }

}