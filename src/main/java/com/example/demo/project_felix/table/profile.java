package com.example.demo.project_felix.table;

import java.util.Date;
import javax.persistence.*;

import com.example.demo.n_model.custom_response.*;

import org.hibernate.annotations.GenericGenerator;
import org.joda.time.DateTime;

import lombok.*;

@Entity
@Getter
@Setter

public class profile {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String password;
    @Column(length = 50)
    private String email;
    @Column(columnDefinition = "DATE")
    private Date dob;
    @Column(columnDefinition = "LONGTEXT")
    private String address;
    @Column(length = 50)
    private String gender;

    @Column(length = 50)
    private String onesignalid;
    @Column(length = 50)
    private Integer statuslogin;

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

    public profile() {

    }

    public profile(model_register model) {
        setName(model.getName());
        setPassword(model.getPassword());
        setEmail(model.getEmail());
        setDob(new DateTime(model.getDob()).toDate());
        setAddress(model.getAddress());
        setGender(model.getGender());
        setStatuslogin(0);

        setCreatedby("SYSTEM");
        setCreatedat(new Date());
    }

    public void edit(model_profile model) {
        setName(model.getName());
        if(!model.getPassword().equals("")){
            setPassword(model.getPassword());
        }
        //  setEmail(model.getEmail());
        setDob(new DateTime(model.getDob()).toDate());
        setAddress(model.getAddress());
        setGender(model.getGender());

        setLastmodifiedat(new Date());
        setLastmodifiedby(model.getId());
        //setVersion(model.getVersion() + 1);

    }

    public void change_pass(model_profile model) {
        if(!model.getPassword().equals("")){
            setPassword(model.getPassword());
        }
    }
}