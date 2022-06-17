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
public interface rep_t_trivia_user extends JpaRepository<task_trivia_user, String> {

    @Query(value = "SELECT * from task_trivia_user where tanggal <= :a AND tanggal >= :b AND idtrivia = :c AND idprofile = :d", nativeQuery = true)
    task_trivia_user findDuplicateMonthly(@Param("a") Date startofmonth, @Param("b") Date endofmonth,
            @Param("c") String idtrivia, @Param("d") String idprofile);

    task_trivia_user findByIdtask(String idtask);

}