package com.example.ex15_jpa_error_controll.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ex15_jpa_error_controll.entity.MyUser;

// jpa의 각종 메소드를 사용하는 부분
public interface MyUserRepository extends JpaRepository<MyUser, String> {
  
}
