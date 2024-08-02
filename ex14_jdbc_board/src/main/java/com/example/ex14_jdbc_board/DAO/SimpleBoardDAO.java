package com.example.ex14_jdbc_board.DAO;

import java.util.List;

import com.example.ex14_jdbc_board.DTO.SimpleBoardDTO;

public interface SimpleBoardDAO {

  public List<SimpleBoardDTO> listDAO(); // 전체 데이터 조회
  public SimpleBoardDTO viewDAO(String id); // 특정 데이터 상세보기
  public int writeDAO(String writer, String title, String content); // 데이터 작성
  public int deleteDAO(String id); // 데이터 삭제
} 
