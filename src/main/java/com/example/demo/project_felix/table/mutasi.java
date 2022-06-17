package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class mutasi {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;

    @Column(length = 40)
    private String createdby;
    @Column(columnDefinition = "DATETIME")
    private Date createdat;
    @Column(length = 40)
    private String lastmodifiedby;
    @Column(columnDefinition = "DATETIME")
    private Date lastmodifiedat;
    @Column(length = 11)
    private Integer version;
    @Column(columnDefinition = "DATETIME")
    private String deletedat;
    
}