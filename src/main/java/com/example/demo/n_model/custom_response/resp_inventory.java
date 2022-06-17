package com.example.demo.n_model.custom_response;

import com.example.demo.project_felix.table.shopitem;
import com.example.demo.project_felix.table.shopitemdetail;

import lombok.*;

@Getter
@Setter

public class resp_inventory {

    public resp_inventory(shopitem shopitem, shopitemdetail shopitemdetail) {
        setNama(shopitem.getNama());
        setKode(shopitemdetail.getKode());
    }

    private String nama;
    private String kode;

}