package com.example.demo.project_felix.controller;

import java.util.*;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;

import com.example.demo.n_model.custom_response.*;
import com.example.demo.n_model.response.http_response;
import com.example.demo.project_felix.repo.*;
import com.example.demo.project_felix.scheduler.populate;
import com.example.demo.project_felix.table.*;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user/")
public class user_controller {

    @Autowired
    private rep_profile rep_profile;

    @Autowired
    private rep_dmmessage rep_dmmessage;

    @Autowired
    private rep_shopitem rep_shopitem;
    @Autowired
    private rep_shopitemdetail rep_shopitemdetail;

    @Autowired
    private rep_inventory rep_inventory;
    @Autowired
    private rep_inventory_detail rep_inventory_detail;

    @Autowired
    private rep_saldo rep_saldo;
    @Autowired
    private populate populate;

    @PostMapping(path = "email")
    public ResponseEntity<http_response> coba_email() {

        http_response resp = new http_response();
        try {

            String response = sendSimpleMessage();
            String verif = verif();
            System.out.println(response);
            System.out.println(verif);
            resp.setSuccess();
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }

    // done
    @PostMapping(path = "login")
    public ResponseEntity<http_response> Login(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            System.out.print(message);

            model_login model = new Gson().fromJson(message, model_login.class);
            profile login_profile_1 = rep_profile.findByEmail(model.getEmail());

            if (login_profile_1 != null) {
                if (model.getPassword().equals(login_profile_1.getPassword())) {

                    // if (login_profile_1.getStatuslogin() == 0) {
                        login_profile_1.setStatuslogin(1);
                        // utang ku disini
                        // login_profile_1.setOnesignalid(login_profile_1.getId());
                        rep_profile.save(login_profile_1);

                        List<resp_profile> updatedProfile = new ArrayList<>();
                        saldopoint saldopoint = rep_saldo.findByIdprofile(login_profile_1.getId());
                        updatedProfile.add(new resp_profile(login_profile_1, saldopoint));
                        resp.setSuccessWithData(updatedProfile);
                    // } else {
                    //     resp.setFail();
                    //     resp.setMessage("Sudah Login Sebelumnya");
                    // }
                } else {
                    resp.setFail();
                    resp.setMessage("Email & Password Salah");
                }
            } else {
                resp.setFail();
                resp.setMessage("User Tidak Ditemukan");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // done
    @PostMapping(path = "logout")
    public ResponseEntity<http_response> Logout(@RequestBody String message) {

        http_response resp = new http_response();
        try {

            model_login model = new Gson().fromJson(message, model_login.class);
            profile login_profile_1 = rep_profile.findByEmail(model.getEmail());

            if (login_profile_1 != null) {
                if (model.getPassword().equals(login_profile_1.getPassword())) {

                    // if (login_profile_1.getStatuslogin() == 1) {

                        login_profile_1.setStatuslogin(0);

                        List<profile> list_dftr = new ArrayList<>();
                        list_dftr.add(login_profile_1);
                        rep_profile.save(login_profile_1);

                        resp.setSuccess();
                    // } else {
                    //     resp.setFail();
                    //     resp.setMessage("Already Logout");
                    // }

                } else {
                    resp.setFail();
                    resp.setMessage("tidak sesuai");
                }
            } else {
                resp.setFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // done
    @PostMapping(path = "register")
    public ResponseEntity<http_response> Register(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_register model = new Gson().fromJson(message, model_register.class);

            if (rep_profile.findByEmail(model.getEmail()) == null) {
                profile new_profile = new profile(model);
                rep_profile.save(new_profile);

                // new saldo
                saldopoint saldo = new saldopoint(new_profile);
                rep_saldo.save(saldo);

                // new inventory
                inventory inventory = new inventory(new_profile);
                rep_inventory.save(inventory);

                // generate task daily
                // generate task weekly
                populate.populate_trivia(new_profile);
                populate.populate_task_daily(new_profile);
                populate.populate_task_weekly(new_profile);

                resp.setSuccess();
            } else {
                resp.setFail();
                resp.setMessage("Email Sudah Teregistrasi");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // done
    @PostMapping(path = "profile")
    public ResponseEntity<http_response> get_profile(@RequestBody String message) {

        http_response resp = new http_response();
        try {

            System.out.println(message);
            model_login model = new Gson().fromJson(message, model_login.class);
            profile profile = rep_profile.findByEmail(model.getEmail());

            List<resp_profile> updatedProfile = new ArrayList<>();
            saldopoint saldopoint = rep_saldo.findByIdprofile(profile.getId());
            updatedProfile.add(new resp_profile(profile, saldopoint));
            // tambahi saldo
            resp.setSuccessWithData(updatedProfile);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // done
    @PostMapping(path = "edit_profile")
    public ResponseEntity<http_response> edit_profile(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            System.out.println(message);
            model_profile model = new Gson().fromJson(message, model_profile.class);
            profile profile_check = rep_profile.findById(model.getId()).get();

            if (profile_check != null) {

                profile_check.edit(model);
                rep_profile.save(profile_check);

                List<resp_profile> updatedProfile = new ArrayList<>();
                saldopoint saldopoint = rep_saldo.findByIdprofile(profile_check.getId());
                updatedProfile.add(new resp_profile(profile_check, saldopoint));
                resp.setSuccessWithData(updatedProfile);
            } else {
                resp.setFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @PostMapping(path = "change_pass")
    public ResponseEntity<http_response> change_pass(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            System.out.println(message);
            model_profile model = new Gson().fromJson(message, model_profile.class);
            profile profile_check = rep_profile.findByEmail(model.getEmail());

            if (profile_check != null) {

                profile_check.change_pass(model);
                rep_profile.save(profile_check);

                List<resp_profile> updatedProfile = new ArrayList<>();
                saldopoint saldopoint = rep_saldo.findByIdprofile(profile_check.getId());
                updatedProfile.add(new resp_profile(profile_check, saldopoint));
                resp.setSuccessWithData(updatedProfile);
            } else {
                resp.setFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    @PostMapping(path = "inventory")
    public ResponseEntity<http_response> get_inventory(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_profile model = new Gson().fromJson(message, model_profile.class);

            inventory inventory = rep_inventory.findByIdprofile(model.getId());
            List<inventorydetail> l_i = rep_inventory_detail.findByIdinventory(inventory.getId());
            List<resp_inventory> input_data = new ArrayList<>();

            for (inventorydetail inventorydetail : l_i) {
                shopitemdetail shopitemdetail = rep_shopitemdetail.findById(inventorydetail.getIditemdetail()).get();
                shopitem shopitem = rep_shopitem.findById(shopitemdetail.getIdshopitem()).get();

                input_data.add(new resp_inventory(shopitem, shopitemdetail));
            }
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    // done
    @PostMapping(path = "message")
    public ResponseEntity<http_response> message(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            System.out.println(message);
            model_message model = new Gson().fromJson(message, model_message.class);
            profile p = rep_profile.findById(model.getId()).get();
            dmmessage msg = new dmmessage(model,p.getName());
            rep_dmmessage.save(msg);
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);

    }

    public static String sendSimpleMessage() throws UnirestException {
        String YOUR_DOMAIN_NAME = "dev.segen.id";
        String API_KEY = "6036ea9e7691fce7528d81b5e1dc436e-a2b91229-963bb7f1";
        HttpResponse<String> request = Unirest.post("https://api.mailgun.net/v3/" + YOUR_DOMAIN_NAME + "/messages")
                .basicAuth("api", API_KEY).queryString("from", "Ian <ian@segen.id>")
                .queryString("to", "adrhighland@gmail.com").queryString("subject", "hello")
                .queryString("text", "testing").asString();
        return request.getBody();
    }

    public static String verif() throws UnirestException {
        // String YOUR_DOMAIN_NAME = "dev.segen.id";
        String API_KEY = "6036ea9e7691fce7528d81b5e1dc436e-a2b91229-963bb7f1";
        HttpResponse<String> request = Unirest.get("https://api.mailgun.net/v4/address/validate")
                .basicAuth("api", API_KEY).queryString("address", "adrhighland@mailgun.com").asString();
        return request.getBody();
    }
}