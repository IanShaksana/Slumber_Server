package com.example.demo.project_felix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.demo.n_model.custom_response.*;
import com.example.demo.n_model.response.http_response;
import com.example.demo.project_felix.repo.rep_dmmessage;
import com.example.demo.project_felix.repo.rep_inventory_detail;
import com.example.demo.project_felix.table.dmmessage;
import com.example.demo.project_felix.table.inventorydetail;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/api/merchant/")
public class merchant_controller {

    @Autowired
    private rep_dmmessage rep_dmmessage;
    @Autowired
    private rep_inventory_detail rep_inventory_detail;

    @PostMapping(path = "get/claim")
    public ResponseEntity<http_response> GET_claim() {

        http_response resp = new http_response();
        try {
            List<inventorydetail> l_all = rep_inventory_detail.findAll();
            resp.setSuccessWithData(l_all);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "get/message")
    public ResponseEntity<http_response> GET_message() {
        http_response resp = new http_response();
        try {
            List<dmmessage> l_all = rep_dmmessage.findAll();
            resp.setSuccessWithData(l_all);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "mark/claim")
    public ResponseEntity<http_response> mark_claim(@RequestBody String message) {
        http_response resp = new http_response();
        try {
            model_id_only model = new Gson().fromJson(message, model_id_only.class);
            inventorydetail msg = rep_inventory_detail.findById(model.getId()).get();
            if (msg.getStatus() == 1) {
                msg.setStatus(0);
            } else {
                msg.setStatus(1);
            }

            rep_inventory_detail.save(msg);
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "mark/message")
    public ResponseEntity<http_response> mark_message(@RequestBody String message) {
        http_response resp = new http_response();
        try {
            model_id_only model = new Gson().fromJson(message, model_id_only.class);
            dmmessage msg = rep_dmmessage.findById(model.getId()).get();
            System.out.println("Statu : " + msg.getStatus());
            if (msg.getStatus() == 1) {
                System.out.println("masuk 1");

                msg.setStatus(0);
            } else {
                System.out.println("masuk 0");

                msg.setStatus(1);
            }

            rep_dmmessage.save(msg);
            resp.setSuccess();
            ;
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping(path = "random")
    public ResponseEntity<http_response> rand_question(@RequestBody String message) {
        http_response resp = new http_response();
        try {

            Random rand = new Random();
            int randomNum = rand.nextInt(((5 - 1) - 0) + 1) + 0;
            model_question model = new model_question(randomNum);
            List<model_question> input_data = new ArrayList<>();
            input_data.add(model);
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}
