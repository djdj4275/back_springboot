package com.example.ex16_mybatis_board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex16_mybatis_board.dto.BoardDTO;

@Mapper
public interface BoardDAO {

  public List<BoardDTO> listDAO(); // 전체 데이터 조회
  public BoardDTO viewDAO(String id); // 특정 데이터 상세보기
  public int writeDAO(String writer, String title, String content); // 데이터 작성
  public int deleteDAO(String id); // 데이터 삭제
} 
