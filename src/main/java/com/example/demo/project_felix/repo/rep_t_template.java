package com.example.demo.project_felix.repo;

import com.example.demo.project_felix.table.task_template;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_t_template extends JpaRepository<task_template,String> {
    List<task_template> findByPeriod(String period);

}