package com.example.demo.n_model.custom_response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.demo.project_felix.table.profile;
import com.example.demo.project_felix.table.saldopoint;

import lombok.*;

@Getter
@Setter

public class resp_profile {

    public resp_profile(profile profile, saldopoint saldopoint) {
        setId(profile.getId());

        setEmail(profile.getEmail());
        setPassword(profile.getPassword());
        setName(profile.getName());
        Calendar c = Calendar.getInstance();
        c.setTime(profile.getDob());
        SimpleDateFormat format_1 = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = format_1.format(c.getTime());
        setDob(current_date);
        setAddress(profile.getAddress());
        setPoint(saldopoint.getPoint());
        setGender(profile.getGender());

        setVersion(profile.getVersion());

    }

    private String id;

    private String email;
    private String password;
    private String name;
    private String dob;
    private String address;
    private Integer point;
    private String gender;

    private Integer version;

}