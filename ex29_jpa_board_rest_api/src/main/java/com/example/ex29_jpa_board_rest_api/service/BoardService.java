package com.example.ex29_jpa_board_rest_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.ex29_jpa_board_rest_api.entity.Board;
import com.example.ex29_jpa_board_rest_api.repository.BoardRepository;

import java.util.List;

@Service
public class BoardService {
  
  @Autowired
  private BoardRepository boardRepository;

  // 전체조회
  public List<Board> list() {
    return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id")); // id 기준 내림차순 정렬
  }

  // 개별조회
  public Board view(int id) {
    return boardRepository.findById(id).orElse(null);
  }

  // 데이터 저장
  // public void write(String writer, String title, String content) {
  //   Board board = new Board();

  //   board.setWriter(writer);
  //   board.setTitle(title);
  //   board.setContent(content);

  //   boardRepository.save(board);
  // }

  // repository에 추상메소드로 만들어둔 커스텀한 쿼리메소드를 여기서 구현해 사용
  public void write(String writer, String title, String content) {
    boardRepository.write(writer, title, content);
  }

  // 데이터 삭제
  public void delete(int id) {
    boardRepository.deleteById(id);
  }
}
