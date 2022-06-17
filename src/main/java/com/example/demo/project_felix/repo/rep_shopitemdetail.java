package com.example.demo.project_felix.repo;

import java.util.List;

import com.example.demo.project_felix.table.shopitemdetail;

import org.springframework.data.repository.CrudRepository;

/*
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface rep_shopitemdetail extends CrudRepository<shopitemdetail, String> {

    List<shopitemdetail> findByStatusclaim(Integer statusclaim);

}
