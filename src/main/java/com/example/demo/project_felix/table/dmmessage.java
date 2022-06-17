package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;

import com.example.demo.n_model.custom_response.model_message;

import org.hibernate.annotations.GenericGenerator;
import lombok.*;

@Entity
@Getter
@Setter

public class dmmessage {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;
    @Column(length = 50)
    private String idprofile;
    @Column(length = 50)
    private String email;
    @Column(length = 50)
    private String nama;
    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Column(length = 50)
    private Integer status = 0;

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

    public dmmessage() {
    }

    public dmmessage(model_message model, String nama) {
        setIdprofile(model.getId());
        setEmail(model.getEmail());
        setDescription(model.getMessage());
        setNama(nama);

        setCreatedat(new Date());
        setCreatedby("SYSTEM");
    }

}
