package com.example.ex15_mybatis.jdbc;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

// DAO는 DTO 객체를 받아서 내부의 데이터를 데이테베이서에 직접적으로 넣어주는 역할 (레파지토리 역할)
// mybatis 버전

// 인터페이스는 자체 객체를 만들지 못하는데 Mapper 어노테이션이 붙으면
// xml과 연동되어서 사용할수있게됨
@Mapper
public interface MyUserDAO {
  List<MyUserDTO> list();
}
