package com.example.ex19_mybatis_sql_log.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyUserDAO {
    List<MyUserDTO> list();
} 
