package com.example.ex15_jpa_error_controll.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

// 엔티티는 db의 테이블과 1:1로 매핑되는 객체 (DTO의 역할)
@Entity
@Data
@Table(name = "myuser") // myuser 테이블과 매핑됨.
public class MyUser {
  @Id // 프라이머리 키에 해당하는 객체에 써주면됨.
  private String id;
  private String name;
}
