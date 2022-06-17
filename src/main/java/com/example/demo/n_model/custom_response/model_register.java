package com.example.demo.n_model.custom_response;

import java.util.Date;

import lombok.*;

@Getter
@Setter

public class model_register {

    public model_register(){
        
    }

    private String email;
    private String password;
    private String name;
    private String dob;
    private String address;
    private String gender;

}