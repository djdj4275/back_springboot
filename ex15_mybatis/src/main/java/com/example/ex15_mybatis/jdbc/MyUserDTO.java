package com.example.ex15_mybatis.jdbc;

import lombok.Data;

// db에서 들고올 데이터의 형태(구조체)
@Data
public class MyUserDTO {
  private String id;
  private String name;
}
