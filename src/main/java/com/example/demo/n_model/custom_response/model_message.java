package com.example.demo.n_model.custom_response;


import lombok.*;

@Getter
@Setter

public class model_message {

    public model_message(){
        
    }
    
    private String id;
    private String email;
    private String message;

    private Integer version;

}