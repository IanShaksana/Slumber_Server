package com.example.demo.n_model.custom_response;

import java.util.Date;

import lombok.*;

@Getter
@Setter

public class model_sleepdata {

    public model_sleepdata() {

    }

    private Integer avg_insomina = 0;
    private Integer avg_sleep_time = 0;
    private Date sleep_time;
    private Date wake_time;

}