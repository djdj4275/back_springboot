package com.example.ex20_service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.ex20_service.dto.BoardDTO;

@Mapper
public interface BoardDAO {

  public List<BoardDTO> listDAO(); // 전체 데이터 조회
  public BoardDTO viewDAO(String id); // 특정 데이터 상세보기
  public int writeDAO(Map<String, String> map); // 데이터 작성
  public int deleteDAO(@Param("_id") String id); // 데이터 삭제

  public int articleCount();
} 
