package com.example.demo.project_felix.repo;

import com.example.demo.project_felix.table.sleepdata;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_sleepdata extends JpaRepository<sleepdata, String> {
    @Query(value = "SELECT * from sleepdata where tanggal >= :a AND tanggal <= :b  AND idprofile = :d order by tanggalwaktu", nativeQuery = true)
    List<sleepdata> findThisWeek(@Param("a") Date start, @Param("b") Date end, @Param("d") String idprofile);

    @Query(value = "SELECT * from sleepdata where tanggal = :a  AND idprofile = :b order by tanggalwaktu AND jenis = 'WAKE'", nativeQuery = true)
    sleepdata findWakeSync(@Param("a") Date start, @Param("b") String idprofile);

}