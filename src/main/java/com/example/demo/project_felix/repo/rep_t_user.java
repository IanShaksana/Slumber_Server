package com.example.demo.project_felix.repo;

import java.util.Date;

import com.example.demo.project_felix.table.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/*
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_t_user extends JpaRepository<task_user, String> {
        @Query(value = "SELECT * from task_user where tanggal <= :a AND tanggal >= :b AND idtemplate = :c AND idprofile = :d  AND period = 'DAILY'", nativeQuery = true)
        task_user findDuplicateDaily(@Param("a") Date startofmonth, @Param("b") Date endofmonth,
                        @Param("c") String idtemplate, @Param("d") String idprofile);

        @Query(value = "SELECT * from task_user where tanggal <= :a AND tanggal >= :b AND idtemplate = :c AND idprofile = :d  period = 'WEEKLY'", nativeQuery = true)
        task_user findDuplicateMonthly(@Param("a") Date startofmonth, @Param("b") Date endofmonth,
                        @Param("c") String idtemplate, @Param("d") String idprofile);

        @Query(value = "SELECT * from task_user where idprofile = :a AND period = 'DAILY' and status = 1", nativeQuery = true)
        task_user findLatestDaily(@Param("a") String idprofile);

        @Query(value = "SELECT * from task_user where idprofile = :a AND period = 'WEEKLY' and status = 1", nativeQuery = true)
        task_user findLatestMonthly(@Param("a") String idprofile);

        @Query(value = "SELECT * from task_user where idprofile = :a AND period = 'TRIVIA' and status = 1", nativeQuery = true)
        task_user findLatestTrivia(@Param("a") String idprofile);

        List<task_user> findByStatusAndPeriod(Integer status, String Period);

        @Query(value = "SELECT * from task_user where idprofile = :a AND status = 1 AND tipe = :b", nativeQuery = true)
        List<task_user> findActiveByUser(@Param("a") String idprofile, @Param("b") String tipe);

}