package com.example.demo.project_felix.repo;

import java.util.Date;

import com.example.demo.project_felix.table.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_t_trivia extends JpaRepository<task_trivia, String> {
    @Query(value = "SELECT * from task_trivia where tanggal <= :a AND tanggal >= :b AND idtemplate = :c AND period = 'DAILY'", nativeQuery = true)
    task_trivia findDuplicateDaily(@Param("a") Date startofweek, @Param("b") Date endofweek,
            @Param("c") String idtemplate);

    @Query(value = "SELECT * from task_trivia where tanggal <= :a AND tanggal >= :b AND idtemplate = :c AND period = 'WEEKLY'", nativeQuery = true)
    task_trivia findDuplicateMonthly(@Param("a") Date startofmonth, @Param("b") Date endofmonth,
            @Param("c") String idtemplate);
}