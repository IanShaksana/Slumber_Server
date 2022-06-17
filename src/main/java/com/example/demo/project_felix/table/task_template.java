package com.example.demo.project_felix.table;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class task_template {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;

    // article, music, alarm
    @Column(length = 50)
    private String tipe;
    // weekly, daily
    @Column(length = 50)
    private String period;

    // berapa kali nya, kalo music dia ini itungan menit
    @Column(length = 50)
    private Integer repetisi;

    // 0 no continous, 1 berturut - turut
    @Column(length = 50)
    private Integer continous;

    // jam detail (untuk alarm) template
    @Column(length = 50)
    private String idalarm;

    @Column(length = 50)
    private Integer point;

    // nama tasknya
    @Column(columnDefinition = "LONGTEXT")
    private String deskripsi;

}