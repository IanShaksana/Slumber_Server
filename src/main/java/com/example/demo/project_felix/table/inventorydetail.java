package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class inventorydetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idinventory;

    @Column(length = 50)
    private String idprofile;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String nama;
    @Column(length = 50)
    private String iditemdetail;

    @Column(length = 50)
    private Integer status = 0;

    @Column(columnDefinition = "LONGTEXT")
    private String deskripsi;

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

    public inventorydetail() {
    }


    public inventorydetail(inventory inv, shopitem itm, profile p) {
        setIdinventory(inv.getId());
        setIditemdetail(itm.getId());
        setIdprofile(p.getId());
        setNama(p.getName());
        setEmail(p.getEmail());

        setCreatedby("SYSTEM");
        setCreatedat(new Date());
    }

}