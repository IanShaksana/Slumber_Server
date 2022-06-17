package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class sleepdata {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idprofile;

    // sleep
    // wake
    @Column(length = 50)
    private String jenis;

    @Column(columnDefinition = "DATETIME")
    private Date tanggalwaktu;
    @Column(columnDefinition = "DATE")
    private Date tanggal;
    @Column(columnDefinition = "TIME")
    private Date waktu;

}