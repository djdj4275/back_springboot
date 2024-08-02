package com.example.ex13_jdbc.jdbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// DAO는 DTO 객체를 받아서 내부의 데이터를 데이테베이서에 직접적으로 넣어주는 역할 (레파지토리 역할)
@Repository
public class MyUserDAO {
    @Autowired // 이 어노테이션으로 아래의 값은 bean으로 취급됨.
    private JdbcTemplate jdbcTemplate;

    public List<MyUserDTO> list() {
      String query = "SELECT * FROM myuser";

      // db에서 위의 query문(SQL문)으로 해당하는 값을 list로 들고와서 리턴
      // 이때 db의 값을 들고오는 형태(구조체)는 미리 MyUserDTO라고 정의해둠
      List<MyUserDTO> list = jdbcTemplate.query(query, new BeanPropertyRowMapper<MyUserDTO>(MyUserDTO.class));
      return list;
    }
}
