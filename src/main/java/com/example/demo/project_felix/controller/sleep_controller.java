package com.example.demo.project_felix.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.demo.n_model.custom_response.model_profile;
import com.example.demo.n_model.custom_response.model_sleepdata;
import com.example.demo.n_model.response.http_response;
import com.example.demo.project_felix.repo.rep_sleepdata;
import com.example.demo.project_felix.table.sleepdata;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/report/")
public class sleep_controller {
    @Autowired
    private rep_sleepdata rep_sleepdata;

    @PostMapping(path = "get")
    public ResponseEntity<http_response> get_report(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            System.out.println("get report");

            System.out.println("message");
            model_profile profile = new Gson().fromJson(message, model_profile.class);
            Date today = new Date();
            Date start = new DateTime(today).toLocalDate().dayOfWeek().withMinimumValue().toDate();
            Date end = new DateTime(today).toLocalDate().dayOfWeek().withMaximumValue().toDate();

            System.out.println(start);
            System.out.println(end);

            List<sleepdata> l_data_this_week = rep_sleepdata.findThisWeek(start, end, profile.getId());
            model_sleepdata data = new model_sleepdata();

            Integer count_sleep = 0;
            Integer total_sleep = 0;
            Integer avg_total_sleep = 0;

            Integer count_sleeping_time = 0;
            Integer total_sleeping_time_hour = 0;
            Integer total_sleeping_time_minute = 0;
            Integer avg_total_sleeping_time_hour = 0;
            Integer avg_total_sleeping_time_minute = 0;

            Integer count_wake_up_time = 0;
            Integer total_wake_up_time_hour = 0;
            Integer total_wake_up_time_minute = 0;
            Integer avg_total_wake_up_time_hour = 0;
            Integer avg_total_wake_up_time_minute = 0;

            System.out.println(l_data_this_week.size());

            for (sleepdata sleepdata : l_data_this_week) {

                // avg insomnia
                if (sleepdata.getJenis().equalsIgnoreCase("SLEEP")) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(sleepdata.getTanggalwaktu());
                    c.set(Calendar.HOUR_OF_DAY, 23);
                    c.set(Calendar.MINUTE, 0);
                    c.set(Calendar.SECOND, 0);
                    c.set(Calendar.MILLISECOND, 0);

                    if (sleepdata.getWaktu().after(c.getTime())) {
                        data.setAvg_insomina(data.getAvg_insomina() + 1);
                    }
                }

                // sleep time
                if (sleepdata.getJenis().equalsIgnoreCase("SLEEP")) {
                    LocalDate date_sleep = new DateTime(sleepdata.getTanggal()).toLocalDate();
                    LocalDate date_wake = date_sleep.plusDays(1);

                    sleepdata wakedata = rep_sleepdata.findWakeSync(date_wake.toDate(), profile.getId());

                    if (wakedata != null) {
                        System.out.println(date_wake);
                        Date d1 = sleepdata.getTanggalwaktu();
                        Date d2 = wakedata.getTanggalwaktu();

                        long difference_In_Time = d2.getTime() - d1.getTime();
                        long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60));
                        Integer i = (int) (long) difference_In_Hours;

                        System.out.println(difference_In_Time);
                        System.out.println(difference_In_Hours);
                        System.out.println(i);
                        total_sleep = total_sleep + i;
                        count_sleep++;
                    }

                }

                // avg sleep time
                if (sleepdata.getJenis().equalsIgnoreCase("SLEEP")) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(sleepdata.getTanggalwaktu());

                    count_sleeping_time++;
                    total_sleeping_time_hour = total_sleeping_time_hour + c.get(Calendar.HOUR_OF_DAY);
                    total_sleeping_time_minute = total_sleeping_time_minute + c.get(Calendar.MINUTE);
                }

                // avg wake time
                if (sleepdata.getJenis().equalsIgnoreCase("WAKE")) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(sleepdata.getTanggalwaktu());

                    count_wake_up_time++;
                    total_wake_up_time_hour = total_wake_up_time_hour + c.get(Calendar.HOUR_OF_DAY);
                    total_wake_up_time_minute = total_wake_up_time_minute + c.get(Calendar.MINUTE);
                }

            }

            if (count_sleep != 0) {
                avg_total_sleep = total_sleep / count_sleep;
                data.setAvg_sleep_time(avg_total_sleep);
            }

            if (count_sleeping_time != 0) {
                avg_total_sleeping_time_hour = total_sleeping_time_hour / count_sleeping_time;
                avg_total_sleeping_time_minute = total_sleeping_time_minute / count_sleeping_time;
                Calendar c_sleep = Calendar.getInstance();
                c_sleep.set(Calendar.HOUR_OF_DAY, avg_total_sleeping_time_hour);
                c_sleep.set(Calendar.MINUTE, avg_total_sleeping_time_minute);
                data.setSleep_time(c_sleep.getTime());
            }

            if (count_wake_up_time != 0) {
                avg_total_wake_up_time_hour = total_wake_up_time_hour / count_wake_up_time;
                avg_total_wake_up_time_minute = total_wake_up_time_minute / count_wake_up_time;
                Calendar c_wake = Calendar.getInstance();
                c_wake.set(Calendar.HOUR_OF_DAY, avg_total_wake_up_time_hour);
                c_wake.set(Calendar.MINUTE, avg_total_wake_up_time_minute);
                data.setWake_time(c_wake.getTime());
            }

            System.out.println(data.getAvg_insomina());
            System.out.println(data.getAvg_sleep_time());
            System.out.println(data.getSleep_time());
            System.out.println(data.getWake_time());

            List<model_sleepdata> input_data = new ArrayList<>();
            input_data.add(data);
            // tambahi saldo
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "wake")
    public ResponseEntity<http_response> report_sleep(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_profile profile = new Gson().fromJson(message, model_profile.class);
            Date today = new Date();
            sleepdata newWake = new sleepdata();
            newWake.setIdprofile(profile.getId());
            newWake.setJenis("WAKE");
            newWake.setTanggal(today);
            newWake.setTanggalwaktu(today);
            newWake.setWaktu(today);

            rep_sleepdata.save(newWake);

            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "sleep")
    public ResponseEntity<http_response> report_wake(@RequestBody String message) {

        http_response resp = new http_response();
        try {

            model_profile profile = new Gson().fromJson(message, model_profile.class);
            Date today = new Date();
            sleepdata newWake = new sleepdata();
            newWake.setIdprofile(profile.getId());
            newWake.setJenis("SLEEP");
            newWake.setTanggal(today);
            newWake.setTanggalwaktu(today);
            newWake.setWaktu(today);

            rep_sleepdata.save(newWake);

            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}