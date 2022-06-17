package com.example.demo.project_felix.repo;

import java.util.Date;
import java.util.List;

import com.example.demo.project_felix.table.task_music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_t_music extends JpaRepository<task_music, String> {
        @Query(value = "SELECT * from task_music where tanggal =:a AND idtask = :b ", nativeQuery = true)
        task_music findDuplicateDaily(@Param("a") Date tanggal, @Param("b") String idtemplate);

        List<task_music> findByIdtask(String idtask);
}