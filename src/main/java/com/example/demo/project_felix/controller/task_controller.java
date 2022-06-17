package com.example.demo.project_felix.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.demo.n_model.custom_response.model_activity;
import com.example.demo.n_model.custom_response.model_get_task;
import com.example.demo.n_model.custom_response.model_profile;
import com.example.demo.n_model.response.http_response;
// import com.example.demo.project_felix.repo.rep_profile;
import com.example.demo.project_felix.repo.rep_saldo;
import com.example.demo.project_felix.repo.rep_t_alarm;
import com.example.demo.project_felix.repo.rep_t_article;
import com.example.demo.project_felix.repo.rep_t_music;
import com.example.demo.project_felix.repo.rep_t_template;
import com.example.demo.project_felix.repo.rep_t_trivia;
import com.example.demo.project_felix.repo.rep_t_trivia_user;
import com.example.demo.project_felix.repo.rep_t_user;
import com.example.demo.project_felix.table.saldopoint;
import com.example.demo.project_felix.table.task_alarm;
import com.example.demo.project_felix.table.task_article;
import com.example.demo.project_felix.table.task_music;
import com.example.demo.project_felix.table.task_template;
import com.example.demo.project_felix.table.task_trivia;
import com.example.demo.project_felix.table.task_trivia_user;
import com.example.demo.project_felix.table.task_user;
import com.google.gson.Gson;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/task/")
public class task_controller {

    // @Autowired
    // private rep_profile rep_profile;

    @Autowired
    private rep_saldo rep_saldo;

    @Autowired
    private rep_t_user rep_t_user;
    @Autowired
    private rep_t_template rep_t_template;

    @Autowired
    private rep_t_article rep_t_article;
    @Autowired
    private rep_t_music rep_t_music;
    @Autowired
    private rep_t_alarm rep_t_alarm;
    @Autowired
    private rep_t_trivia rep_t_trivia;
    @Autowired
    private rep_t_trivia_user rep_t_trivia_user;

    // done
    @PostMapping(path = "get/daily")
    public ResponseEntity<http_response> get_daily(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_profile model = new Gson().fromJson(message, model_profile.class);

            List<model_get_task> input_data = new ArrayList<>();
            model_get_task data = new model_get_task();

            data.setDaily(rep_t_user.findLatestDaily(model.getId()));
            if (data.getDaily() != null) {
                task_template template_daily = rep_t_template.findById(data.getDaily().getIdtemplate()).get();
                data.setTitle_daily(template_daily.getDeskripsi());
                switch (data.getDaily().getTipe()) {
                    case "ARTICLE":
                        data.setProgress_daily(rep_t_article.findByIdtask(data.getDaily().getId()).size());
                        break;
                    case "MUSIC":
                        data.setProgress_daily(rep_t_music.findByIdtask(data.getDaily().getId()).size());
                        break;
                    case "ALARM":
                        task_alarm detail = rep_t_alarm.findByIdtask(data.getDaily().getId());
                        System.out.println(detail.getCounter());
                        data.setProgress_daily(detail.getCounter());
                        break;
                    case "TRIVIA":
                        break;
                    default:
                        break;
                }
                data.setPoint_daily(template_daily.getPoint());
            }

            data.setWeekly(rep_t_user.findLatestMonthly(model.getId()));
            if (data.getWeekly() != null) {
                task_template template_weekly = rep_t_template.findById(data.getWeekly().getIdtemplate()).get();
                data.setRep_weekly(template_weekly.getRepetisi());
                data.setTitle_weekly(template_weekly.getDeskripsi());
                switch (data.getWeekly().getTipe()) {
                    case "ARTICLE":
                        data.setProgress_weekly(rep_t_article.findByIdtask(data.getWeekly().getId()).size());
                        break;
                    case "MUSIC":
                        data.setProgress_weekly(rep_t_music.findByIdtask(data.getWeekly().getId()).size());
                        break;
                    case "ALARM":
                        task_alarm detail = rep_t_alarm.findByIdtask(data.getWeekly().getId());
                        data.setProgress_weekly(detail.getCounter());
                        break;
                    case "TRIVIA":
                        break;
                    default:
                        break;
                }
                
            data.setPoint_weekly(template_weekly.getPoint());
            }

            task_user trivia_task = rep_t_user.findLatestTrivia(model.getId());
            if (trivia_task != null) {
                task_trivia_user trivia_bind = rep_t_trivia_user.findByIdtask(trivia_task.getId());
                task_trivia trivia_detail = rep_t_trivia.findById(trivia_bind.getIdtrivia()).get();
                data.setTrivia(trivia_task);
                data.setTrivia_detail(trivia_detail);
            }
            input_data.add(data);

            // tambahi saldo
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "get/weekly")
    public ResponseEntity<http_response> get_weekly(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_profile model = new Gson().fromJson(message, model_profile.class);

            List<model_get_task> input_data = new ArrayList<>();
            model_get_task data = new model_get_task();
            data.setDaily(rep_t_user.findLatestDaily(model.getId()));
            data.setWeekly(rep_t_user.findLatestMonthly(model.getId()));
            input_data.add(data);

            // tambahi saldo
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "get/trivia")
    public ResponseEntity<http_response> get(@RequestBody String message) {

        http_response resp = new http_response();
        try {
            model_profile model = new Gson().fromJson(message, model_profile.class);

            List<model_get_task> input_data = new ArrayList<>();
            model_get_task data = new model_get_task();
            data.setDaily(rep_t_user.findLatestDaily(model.getId()));
            data.setWeekly(rep_t_user.findLatestMonthly(model.getId()));
            input_data.add(data);

            // tambahi saldo
            resp.setSuccessWithData(input_data);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "finish")
    public ResponseEntity<http_response> finish(@RequestBody String message) {

        http_response resp = new http_response();
        try {

            model_activity model = new Gson().fromJson(message, model_activity.class);
            List<task_user> l_task = rep_t_user.findActiveByUser(model.getIdprofile(), model.getTipe());

            for (task_user task : l_task) {

                task_template template = rep_t_template.findById(task.getIdtemplate()).get();
                switch (template.getTipe()) {
                    case "ARTICLE":
                        process_article(template, task, model.getIdarticle());
                        break;

                    case "MUSIC":
                        process_music(template, task);
                        break;

                    case "ALARM":
                        process_alarm(template, task, model.getIdalarm());
                        break;

                    case "TRIVIA":
                        process_trivia(template, task, model.getIdtriviauser(), model.getAnswer());
                        break;

                    default:
                        break;
                }
            }

            // tambahi saldo
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping(path = "claim")
    public ResponseEntity<http_response> claim(@RequestBody String message) {

        http_response resp = new http_response();
        try {

            model_activity model = new Gson().fromJson(message, model_activity.class);
            task_user task = rep_t_user.findById(model.getIdtask()).get();

            task_template template = rep_t_template.findById(task.getIdtemplate()).get();
            claim_process(template, task);

            // tambahi saldo
            resp.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setFail();
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    private void process_article(task_template template, task_user task, String message) {
        System.out.println("MASUK SINSI");
        Integer progress = rep_t_article.findByIdtask(task.getId()).size();
        // ini progress

        if (task.getStatus() == 1) {

            if (progress < template.getRepetisi()) {

                task_article n_progress = new task_article(message, task);
                Date today = new Date();
                Date startofweek = new DateTime(today).toLocalDate().dayOfWeek().withMinimumValue().toDate();
                Date endofweek = new DateTime(today).toLocalDate().dayOfWeek().withMaximumValue().toDate();
                if (rep_t_article.findDuplicateMonthly(startofweek, endofweek, task.getId(), message) == null) {
                    rep_t_article.save(n_progress);
                }
            }

            // if (progress == template.getRepetisi()) {
            // task.setStatus(2);
            // rep_t_user.save(task);

            // saldopoint saldopoint = rep_saldo.findByIdprofile(task.getIdprofile());
            // saldopoint.setPoint(saldopoint.getPoint() + template.getPoint());
            // rep_saldo.save(saldopoint);

            // } else {

            // task_article n_progress = new task_article(message, task);
            // Date today = new Date();
            // Date startofweek = new
            // DateTime(today).toLocalDate().dayOfWeek().withMinimumValue().toDate();
            // Date endofweek = new
            // DateTime(today).toLocalDate().dayOfWeek().withMaximumValue().toDate();
            // if (rep_t_article.findDuplicateMonthly(startofweek, endofweek, task.getId(),
            // message) == null) {
            // rep_t_article.save(n_progress);
            // }

            // }

        }

    }

    private void process_music(task_template template, task_user task) {
        Integer progress = rep_t_music.findByIdtask(task.getId()).size();
        // ini progress
        if (task.getStatus() == 1) {

            if (progress < template.getRepetisi()) {

                task_music n_progress = new task_music(task);
                Date today = new Date();
                Date startofweek = new DateTime(today).toLocalDate().toDate();
                if (rep_t_music.findDuplicateDaily(startofweek, task.getId()) == null) {
                    rep_t_music.save(n_progress);
                }
            }

            // if (progress == template.getRepetisi()) {
            // task.setStatus(2);
            // rep_t_user.save(task);

            // saldopoint saldopoint = rep_saldo.findByIdprofile(task.getIdprofile());
            // saldopoint.setPoint(saldopoint.getPoint() + template.getPoint());
            // rep_saldo.save(saldopoint);

            // } else {

            // task_music n_progress = new task_music(task);
            // Date today = new Date();
            // Date startofweek = new DateTime(today).toLocalDate().toDate();
            // if (rep_t_music.findDuplicateDaily(startofweek, task.getId()) == null) {
            // rep_t_music.save(n_progress);
            // }

            // }

        }

    }

    private void process_alarm(task_template template, task_user task, String idalarm) {
        // ini progress

        task_alarm detail = rep_t_alarm.findByIdtask(task.getId());
        Boolean check_id_alarm = template.getIdalarm().equalsIgnoreCase(idalarm);

        if (task.getStatus() == 1 && check_id_alarm) {
            
            if (template.getContinous() == 1) {
                LocalDate tanggal_last;
                if(detail.getTanggal() != null){
                    tanggal_last = new DateTime(detail.getTanggal()).toLocalDate();
                }else{
                    tanggal_last = new DateTime(new Date()).toLocalDate();
                    tanggal_last = tanggal_last.minusDays(1);
                }
                LocalDate tanggal_today = new DateTime(new Date()).toLocalDate();

                tanggal_last = tanggal_last.plusDays(1);
                Boolean check = tanggal_last.compareTo(tanggal_today) == 0;
                if (check) {
                    if (detail.getCounter() < template.getRepetisi()) {
                        detail.setCounter(detail.getCounter() + 1);
                        detail.setTanggal(tanggal_today.toDate());
                    }
                    rep_t_alarm.save(detail);
                }

            }
            if (template.getContinous() == 0) {
                LocalDate tanggal_last;
                if(detail.getTanggal() != null){
                    tanggal_last = new DateTime(detail.getTanggal()).toLocalDate();
                }else{
                    tanggal_last = new DateTime(new Date()).toLocalDate();
                }
                LocalDate tanggal_today = new DateTime(new Date()).toLocalDate();


                Boolean check = tanggal_last.compareTo(tanggal_today) != 0;
                if (detail.getCounter() < template.getRepetisi() && check) {
                    detail.setCounter(detail.getCounter() + 1);
                    detail.setTanggal(tanggal_today.toDate());
                }
                rep_t_alarm.save(detail);
            }

        }

    }

    private void claim_process(task_template template, task_user task) {

        Integer progress = 0;

        switch (template.getTipe()) {
            case "ARTICLE":

                progress = rep_t_article.findByIdtask(task.getId()).size();
                if (progress == template.getRepetisi()) {
                    task.setStatus(2);
                    rep_t_user.save(task);

                    saldopoint saldopoint = rep_saldo.findByIdprofile(task.getIdprofile());
                    saldopoint.setPoint(saldopoint.getPoint() + template.getPoint());
                    rep_saldo.save(saldopoint);
                }
                break;

            case "MUSIC":
                progress = rep_t_music.findByIdtask(task.getId()).size();
                if (progress == template.getRepetisi()) {
                    task.setStatus(2);
                    rep_t_user.save(task);

                    saldopoint saldopoint = rep_saldo.findByIdprofile(task.getIdprofile());
                    saldopoint.setPoint(saldopoint.getPoint() + template.getPoint());
                    rep_saldo.save(saldopoint);
                }
                break;

            case "ALARM":

                task_alarm detail = rep_t_alarm.findByIdtask(task.getId());
                if (detail.getCounter() == template.getRepetisi()) {
                    task.setStatus(2);
                    rep_t_user.save(task);

                    saldopoint saldopoint = rep_saldo.findByIdprofile(task.getIdprofile());
                    saldopoint.setPoint(saldopoint.getPoint() + template.getPoint());
                    rep_saldo.save(saldopoint);
                }
                break;

            default:
                break;
        }
    }

    private void process_trivia(task_template template, task_user task, String idtriviauser, Integer answer) {
        System.out.println(idtriviauser);
        task_trivia_user ttu = rep_t_trivia_user.findByIdtask(task.getId());
        task_trivia tt = rep_t_trivia.findById(ttu.getIdtrivia()).get();

        task.setStatus(2);
        rep_t_user.save(task);

        if (tt.getAnswer() == answer) {
            saldopoint saldopoint = rep_saldo.findByIdprofile(task.getIdprofile());
            saldopoint.setPoint(saldopoint.getPoint() + template.getPoint());
            rep_saldo.save(saldopoint);
        }

    }

}