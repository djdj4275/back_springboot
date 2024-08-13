package com.example.ex15_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ex15_jpa.entity.MyUser;
import com.example.ex15_jpa.repository.MyUserRepository;

import java.util.List;

// 레파지토리의 기능들을 여기서 실행 (비즈니스 로직)
@Service
public class MyUserService {

  @Autowired
  private MyUserRepository myUserRepository;

  public List<MyUser> list() {
    return myUserRepository.findAll(); // 이 한줄로 db의 myuser테이블에 있는 데이터를 전부 불러옴
  }
}
