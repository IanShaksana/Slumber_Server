package com.example.demo.project_felix.repo;

import java.util.Date;

import com.example.demo.project_felix.table.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_t_alarm extends JpaRepository<task_alarm, String> {
        @Query(value = "SELECT * from task_alarm where tanggal <= :b AND tanggal >= :a AND idtemplate = :b AND period = 'DAILY'", nativeQuery = true)
        task_alarm findDuplicateDaily(@Param("a") Date startofweek, @Param("a") Date endofweek,
                        @Param("b") String idtemplate);

        @Query(value = "SELECT * from task_alarm where tanggal >= :b AND tanggal <= :a AND idtemplate = :b AND period = 'WEEKLY'", nativeQuery = true)
        task_alarm findDuplicateMonthly(@Param("a") Date startofmonth, @Param("a") Date endofmonth,
                        @Param("b") String idtemplate);

        task_alarm findByIdtask(String idtask);
}