package com.example.demo.n_model.custom_response;

import lombok.*;

@Getter
@Setter

public class model_activity {

    public model_activity() {

    }

    // credential
    private String idprofile;
    private String idtask;

    // article, music , alarm, trivia
    private String tipe;
    
    // utk detail article
    private String idarticle;

    // utk detail alarm
    private String idalarm;

    // utk detail trivia
    private String idtriviauser;
    private Integer answer;

}