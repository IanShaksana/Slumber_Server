package com.example.demo.n_model.custom_response;


import lombok.*;

@Getter
@Setter

public class model_profile {

    public model_profile(){
        
    }
    
    private String id;

    private String email;
    private String password;
    private String name;
    private String dob;
    private String address;
    private String gender;

    private Integer version;

}