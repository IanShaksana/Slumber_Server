package com.example.demo.n_model.custom_response;

import lombok.*;

@Getter
@Setter

public class model_question {

    public model_question() {

    }

    public model_question(Integer num) {
        setId("Question" + num);
        setQuestion("This is Question Number " + num);
        setAnswer("This is Question Number " + num);

    }

    private String id;

    private String question;
    private String answer;

}