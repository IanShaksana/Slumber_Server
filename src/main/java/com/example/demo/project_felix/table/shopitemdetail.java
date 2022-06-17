package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class shopitemdetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idshopitem;

    @Column(length = 50)
    private String kode;
    @Column(length = 50)
    private Integer statusclaim;    

    @Column(length = 40)
    private String uCreatedBy;
    @Column(columnDefinition = "DATETIME")
    private Date vCreatedAt;
    @Column(length = 40)
    private String wLastModifiedBy;
    @Column(columnDefinition = "DATETIME")
    private Date xLastModifiedAt;
    @Column(length = 11)
    private Integer yVersion;
    @Column(columnDefinition = "DATETIME")
    private String zDeletedAt;
    
}