package com.example.demo.project_felix.scheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.example.demo.project_felix.repo.rep_profile;
import com.example.demo.project_felix.repo.rep_t_alarm;
import com.example.demo.project_felix.repo.rep_t_template;
import com.example.demo.project_felix.repo.rep_t_trivia;
import com.example.demo.project_felix.repo.rep_t_trivia_user;
import com.example.demo.project_felix.repo.rep_t_user;
import com.example.demo.project_felix.table.profile;
import com.example.demo.project_felix.table.task_alarm;
import com.example.demo.project_felix.table.task_template;
import com.example.demo.project_felix.table.task_trivia;
import com.example.demo.project_felix.table.task_trivia_user;
import com.example.demo.project_felix.table.task_user;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class populate {

    @Autowired
    private rep_profile rep_profile;

    @Autowired
    private rep_t_template rep_t_template;
    @Autowired
    private rep_t_alarm rep_t_alarm;
    @Autowired
    private rep_t_trivia rep_t_trivia;

    @Autowired
    private rep_t_trivia_user rep_t_trivia_user;

    @Autowired
    private rep_t_user rep_t_user;

    // example of scheduled task, pleas uncomment one of this

    // Schedule task with fix rate
    // @Scheduled(fixedRate = 10000)

    // Schedule task with delay, will wait after previous task complete
    // @Scheduled(fixedDelay = 10000, initialDelay = 5500)

    // Schedule task with spesific repetion visit cronmaker.com
    // extra note, delete last expression from cronmaker.com

    @Scheduled(cron = "0 0 8 1/1 * ?")
    // @Scheduled(fixedDelay = 10000, initialDelay = 5500)
    public void daily_example() {
        // your scheduled task here
        System.out.println("hello");
        populate_task_daily_end();
        populate_task_trivia_end();

        populate_task_daily(null);
        populate_trivia(null);
    }

    @Scheduled(cron = "0 0 8 ? * MON")
    public void weekly_example() {
        // your scheduled task here
        System.out.println("hello");
        populate_task_weekly_end();
        populate_task_weekly(null);
    }

    public void populate_trivia(profile profile_user) {

        List<task_template> all_task_daily = rep_t_template.findByPeriod("TRIVIA");
        List<profile> all_profile = new ArrayList<>();
        if (profile_user != null) {
            all_profile.add(profile_user);
        } else {
            all_profile = rep_profile.findAll();
        }

        for (profile profile : all_profile) {

            Boolean duplicate = true;
            while (duplicate) {

                Random rand = new Random();
                int randomNum = rand.nextInt(((all_task_daily.size() - 1) - 0) + 1) + 0;
                task_template rand_task = all_task_daily.get(randomNum);
                String idtemplate = rand_task.getId();

                Date startofweek = new DateTime(new Date()).toLocalDate().dayOfWeek().withMinimumValue().toDate();
                Date endofweek = new DateTime(new Date()).toLocalDate().dayOfWeek().withMaximumValue().toDate();
                // check duplicate task

                if (check_user_daily(startofweek, endofweek, idtemplate, profile.getId())) {
                    task_user newTask = new task_user(profile, rand_task);
                    rep_t_user.save(newTask);

                    if (newTask.getTipe().equalsIgnoreCase("TRIVIA")) {

                        List<task_trivia> all_trivia = rep_t_trivia.findAll();

                        Boolean randBoolean = true;
                        while (randBoolean) {
                            Random rand2 = new Random();
                            int randomNum2 = rand2.nextInt(((all_trivia.size() - 1) - 0) + 1) + 0;
                            task_trivia rand_trivia = all_trivia.get(randomNum2);

                            Date startofmonth = new DateTime(new Date()).toLocalDate().dayOfMonth().withMinimumValue()
                                    .toDate();
                            Date endofmonth = new DateTime(new Date()).toLocalDate().dayOfMonth().withMaximumValue()
                                    .toDate();
                            if (check_trivia(startofmonth, endofmonth, rand_trivia.getId(), profile.getId())) {
                                task_trivia_user detail = new task_trivia_user(rand_trivia, newTask, profile);
                                rep_t_trivia_user.save(detail);
                                randBoolean = false;
                            }

                        }

                    }

                    duplicate = false;
                }

            }

        }

    }

    public void populate_task_daily(profile profile_user) {
        List<task_template> all_task_daily = rep_t_template.findByPeriod("DAILY");
        List<profile> all_profile = new ArrayList<>();
        if (profile_user != null) {
            all_profile.add(profile_user);
        } else {
            all_profile = rep_profile.findAll();
        }
        for (profile profile : all_profile) {

            Boolean duplicate = true;
            while (duplicate) {

                Random rand = new Random();
                int randomNum = rand.nextInt(((all_task_daily.size() - 1) - 0) + 1) + 0;
                task_template rand_task = all_task_daily.get(randomNum);
                String idtemplate = rand_task.getId();

                Date startofweek = new DateTime(new Date()).toLocalDate().dayOfWeek().withMinimumValue().toDate();
                Date endofweek = new DateTime(new Date()).toLocalDate().dayOfWeek().withMaximumValue().toDate();
                // check duplicate task

                if (check_user_daily(startofweek, endofweek, idtemplate, profile.getId())) {
                    task_user newTask = new task_user(profile, rand_task);
                    rep_t_user.save(newTask);

                    if (newTask.getTipe().equalsIgnoreCase("ALARM")) {
                        task_alarm detail = new task_alarm(newTask);
                        rep_t_alarm.save(detail);
                    }

                    duplicate = false;
                }

                // switch (rand_task.getTipe()) {
                // case "article":
                // if (check_article_daily(startofweek, endofweek, idtemplate)) {
                // task_article newTask = new task_article(profile, rand_task);
                // rep_t_article.save(newTask);
                // duplicate = false;
                // }
                // break;

                // case "music":
                // if (check_music_daily(startofweek, endofweek, idtemplate)) {
                // task_music newTask = new task_music(profile, rand_task);
                // rep_t_music.save(newTask);
                // duplicate = false;
                // }

                // break;

                // case "alarm":
                // if (check_alarm_daily(startofweek, endofweek, idtemplate)) {
                // task_alarm newTask = new task_alarm(profile, rand_task);
                // rep_t_alarm.save(newTask);
                // duplicate = false;
                // }

                // break;

                // case "trivia":
                // if (check_trivia_daily(startofweek, endofweek, idtemplate)) {
                // task_trivia newTask = new task_trivia(profile, rand_task);
                // rep_t_trivia.save(newTask);
                // duplicate = false;
                // }

                // break;

                // default:

                // break;
                // }

            }

        }
    }

    public void populate_task_weekly(profile profile_user) {
        List<task_template> all_task_daily = rep_t_template.findByPeriod("WEEKLY");
        List<profile> all_profile = new ArrayList<>();
        if (profile_user != null) {
            all_profile.add(profile_user);
        } else {
            all_profile = rep_profile.findAll();
        }
        for (profile profile : all_profile) {
            Random rand = new Random();
            int randomNum = rand.nextInt(((all_task_daily.size() - 1) - 0) + 1) + 0;

            Boolean duplicate = true;
            while (duplicate) {
                task_template rand_task = all_task_daily.get(randomNum);
                String idtemplate = rand_task.getId();

                Date startofweek = new DateTime(new Date()).toLocalDate().dayOfWeek().withMinimumValue().toDate();
                Date endofweek = new DateTime(new Date()).toLocalDate().dayOfWeek().withMaximumValue().toDate();
                // check duplicate task

                if (check_user_monthly(startofweek, endofweek, idtemplate, profile.getId())) {
                    task_user newTask = new task_user(profile, rand_task);
                    rep_t_user.save(newTask);
                    if (newTask.getTipe().equalsIgnoreCase("ALARM")) {
                        task_alarm detail = new task_alarm(newTask);
                        rep_t_alarm.save(detail);
                    }
                    duplicate = false;
                }

            }

        }
    }

    private void populate_task_daily_end() {
        List<task_user> l_t_user = rep_t_user.findByStatusAndPeriod(1, "DAILY");
        for (task_user task_user : l_t_user) {
            task_user.setStatus(0);
            rep_t_user.save(task_user);
        }

    }

    private void populate_task_trivia_end() {
        List<task_user> l_t_user = rep_t_user.findByStatusAndPeriod(1, "TRIVIA");
        for (task_user task_user : l_t_user) {
            task_user.setStatus(0);
            rep_t_user.save(task_user);
        }

    }

    private void populate_task_weekly_end() {
        List<task_user> l_t_user = rep_t_user.findByStatusAndPeriod(1, "WEEKLY");
        for (task_user task_user : l_t_user) {
            task_user.setStatus(0);
            rep_t_user.save(task_user);
        }

    }

    private Boolean check_user_daily(Date startofweek, Date endofweek, String idtemplate, String idprofile) {
        if (rep_t_user.findDuplicateDaily(startofweek, endofweek, idtemplate, idprofile) == null) {
            return true;
        } else {
            return false;
        }

    }

    private Boolean check_user_monthly(Date startofweek, Date endofweek, String idtemplate, String idprofile) {
        if (rep_t_user.findDuplicateDaily(startofweek, endofweek, idtemplate, idprofile) == null) {
            return true;
        } else {
            return false;
        }

    }

    private Boolean check_trivia(Date startofmonth, Date endofmonth, String idtrivia, String idprofile) {
        if (rep_t_trivia_user.findDuplicateMonthly(startofmonth, endofmonth, idtrivia, idprofile) == null) {
            return true;
        } else {
            return false;
        }

    }

}