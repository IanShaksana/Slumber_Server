package com.example.demo.n_model.custom_response;

import com.example.demo.project_felix.table.task_trivia;
import com.example.demo.project_felix.table.task_user;

import lombok.*;

@Getter
@Setter

public class model_get_task {

    public model_get_task() {

    }

    private task_user trivia;
    private task_trivia trivia_detail;
    private task_user daily;
    private task_user weekly;

    private Integer progress_trivia;
    private Integer progress_daily;
    private Integer progress_weekly;
    
    private Integer rep_weekly;

    private String title_daily;
    private String title_weekly;

    private Integer point_daily;
    private Integer point_weekly;

}