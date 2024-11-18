package com.example.ex15_mybatis.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MyUserDAO {
    List<MyUserDTO> list();
} 
