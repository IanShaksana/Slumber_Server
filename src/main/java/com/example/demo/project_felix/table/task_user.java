package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class task_user {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idprofile;
    @Column(length = 50)
    private String idtemplate;

    // article, music, alarm, trivia
    @Column(length = 50)
    private String tipe;
    // weekly, daily
    @Column(length = 50)
    private String period;

    @Column(length = 50)
    private Integer repetisi;

    @Column(columnDefinition = "LONGTEXT")
    private String deskripsi;
    @Column(columnDefinition = "DATE")
    private Date tanggal;

    // 0 deactive, 1 active, 2 finish
    @Column(length = 50)
    private Integer status = 1;

    public task_user() {

    }

    public task_user(profile profile, task_template rand_task) {
        setIdprofile(profile.getId());
        setIdtemplate(rand_task.getId());
        setPeriod(rand_task.getPeriod());
        setTipe(rand_task.getTipe());

        setTanggal(new Date());
    }

}
