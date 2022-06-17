package com.example.demo.project_felix.repo;

import com.example.demo.project_felix.table.task_article;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_t_article extends JpaRepository<task_article, String> {

        @Query(value = "SELECT * from task_article where tanggal <= :b AND tanggal >= :a AND idtemplate = :b AND period = 'DAILY'", nativeQuery = true)
        task_article findDuplicateDaily(@Param("a") Date startofweek, @Param("a") Date endofweek,
                        @Param("b") String idtemplate);

        @Query(value = "SELECT * from task_article where tanggal >= :a AND tanggal <= :b AND idtask = :c AND idarticle = :d ", nativeQuery = true)
        task_article findDuplicateMonthly(@Param("a") Date startofmonth, @Param("b") Date endofmonth,
                        @Param("c") String idtemplate, @Param("d") String idarticle);

        List<task_article> findByIdtask(String idtask);

}