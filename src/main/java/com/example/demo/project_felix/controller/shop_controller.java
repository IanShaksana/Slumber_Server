package com.example.demo.project_felix.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.n_model.custom_response.model_buy_item;
import com.example.demo.n_model.response.http_response;
import com.example.demo.project_felix.repo.rep_inventory;
import com.example.demo.project_felix.repo.rep_inventory_detail;
import com.example.demo.project_felix.repo.rep_profile;
import com.example.demo.project_felix.repo.rep_saldo;
import com.example.demo.project_felix.repo.rep_shopitem;
import com.example.demo.project_felix.table.inventory;
import com.example.demo.project_felix.table.inventorydetail;
import com.example.demo.project_felix.table.profile;
import com.example.demo.project_felix.table.saldopoint;
import com.example.demo.project_felix.table.shopitem;
import com.google.gson.Gson;

@RestController
@RequestMapping(path = "/api/shop/")
public class shop_controller {

    @Autowired
    private rep_shopitem rep_shopitem;

    @Autowired
    private rep_inventory rep_inventory;
    @Autowired
    private rep_inventory_detail rep_inventory_detail;
    @Autowired
    private rep_profile rep_profile;

    @Autowired
    private rep_saldo rep_saldo;

    @GetMapping(path = "get/stock")
    public ResponseEntity<http_response> Login() {

        http_response resp = new http_response();
        try {
            resp.setSuccessWithData(rep_shopitem.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "buy/item")
    public ResponseEntity<http_response> buy(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            System.out.println(message);
            model_buy_item model = new Gson().fromJson(message, model_buy_item.class);

            profile p = rep_profile.findById(model.getIdprofile()).get();
            saldopoint saldo = rep_saldo.findByIdprofile(model.getIdprofile());
            inventory inventory = rep_inventory.findByIdprofile(model.getIdprofile());

            // check stock
            shopitem shopitem = rep_shopitem.findById(model.getIditem()).get();
            Integer sisa_saldo = saldo.getPoint() - shopitem.getPoint();
            System.out.println(saldo.getPoint());
            System.out.println(shopitem.getPoint());
            System.out.println(sisa_saldo);

            if (sisa_saldo > 0) {

                inventorydetail newInventory = new inventorydetail(inventory, shopitem, p);
                newInventory.setDeskripsi(model.getEmail() + "-" + model.getHp());
                rep_inventory_detail.save(newInventory);

                saldo.setPoint(sisa_saldo);
                rep_saldo.save(saldo);

                // shopitemdetail shopitemdetail =
                // rep_shopitemdetail.findByStatusclaim(0).get(0);
                // shopitemdetail.setStatusclaim(1);
                // rep_shopitemdetail.save(shopitemdetail);
                // shopitem.setStockclaim(shopitem.getStockclaim() + 1);
                // shopitem.setStocksisa(shopitem.getStocksisa() - 1);
                // rep_shopitem.save(shopitem);

                resp.setSuccess();
            } else {
                resp.setSuccess();
                resp.setMessage("out of stock");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}