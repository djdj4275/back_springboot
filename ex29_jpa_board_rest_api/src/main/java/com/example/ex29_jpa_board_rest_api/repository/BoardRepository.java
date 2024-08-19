package com.example.ex29_jpa_board_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.ex29_jpa_board_rest_api.entity.Board;

import jakarta.transaction.Transactional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
  
  @Modifying // service의 기본 메소드를 커스텀하기위한 어노테이션
  @Transactional
  @Query("insert into Board(writer, title, content) values (:writer, :title, :content)") // 직접 쿼리를 작성해서 커스텀해서 사용
  // 추상메소드로 만들어놓고 service쪽에서 구현해서 사용
  void write(@Param("writer") String writer,@Param("title") String title,@Param("content") String content);
}
