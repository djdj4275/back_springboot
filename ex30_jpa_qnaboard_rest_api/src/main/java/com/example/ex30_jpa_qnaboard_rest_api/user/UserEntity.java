package com.example.ex30_jpa_qnaboard_rest_api.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor // 생성자 없이 호출하는경우
@AllArgsConstructor // 모든 생성자를 호출하는경우
@Entity
@Table(name = "site_users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id 값이 없을 경우 null 처리

    private String username;

    private String password;

    @Column(unique = true)
    private String email;
}