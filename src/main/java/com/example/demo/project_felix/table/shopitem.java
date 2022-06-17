package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class shopitem {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String nama;
    
    @Column(length = 50)
    private Integer stocktotal;
    @Column(length = 50)
    private Integer stocksisa;
    @Column(length = 50)
    private Integer stockclaim;

    @Column(length = 50)
    private Integer point;

    


    @Column(length = 40)
    private String createdby;
    @Column(columnDefinition = "DATETIME")
    private Date createdat;
    @Column(length = 40)
    private String lastmodifiedby;
    @Column(columnDefinition = "DATETIME")
    private Date lastmodifiedat;
    @Column(length = 11)
    private Integer version = 0;
    @Column(columnDefinition = "DATETIME")
    private String deletedat;
    
}