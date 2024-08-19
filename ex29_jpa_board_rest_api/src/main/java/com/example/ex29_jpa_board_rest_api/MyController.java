package com.example.ex29_jpa_board_rest_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex29_jpa_board_rest_api.entity.Board;
import com.example.ex29_jpa_board_rest_api.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

@RestController // Controller + ResponseBody의 역할을 같이 함
@RequestMapping("/api/board") // 전체 url에 기본적으로 붙은 프리픽스 부분
public class MyController {

  @Autowired
  BoardService boardService;
  
  // default 경로
  @RequestMapping
  public String root() throws Exception {
    return "JPA Board API";
  }

  // 전체 조회
  @RequestMapping(value = "/user-list", method = RequestMethod.GET) // @GetMapping도 가능
  public List<Board> userlistPage(Model model) {
    return boardService.list();
  }

  // 개별 조회 (상세조회)
  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public ResponseEntity<Board> view(@RequestParam("id") int id) {
    Board board = boardService.view(id);
    if (board != null) {
      return ResponseEntity.ok(board); // ResponseEntity란 spring에서 http응답을 생성하고 반환하는데 사용되는 클래스 (응답에 대한 코드, 헤더, 본문을 제어)
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  // 게시글 추가
  // @RequestMapping(value = "/write", method = RequestMethod.POST) // @PostMapping도 가능
  // public ResponseEntity<Board> write(@RequestParam("writer") String writer, @RequestParam("title") String title, @RequestParam("content") String content) {
  //   boardService.write(writer, title, content);

  //   Board newBoard = new Board();
  //   newBoard.setWriter(writer);
  //   newBoard.setTitle(title);
  //   newBoard.setContent(content);

  //   return ResponseEntity.ok(newBoard);
  // }

  // 게시글 추가 (body로 보내기)
  @RequestMapping(value = "/write", method = RequestMethod.POST) // @PostMapping도 가능
  public ResponseEntity<Board> write(@RequestBody Board board) {
    boardService.write(board.getWriter(), board.getTitle(), board.getContent());

    return ResponseEntity.ok(board);
  }

  // 게시글 삭제 
  // @RequestMapping(value = "/delete", method = RequestMethod.DELETE) // @DeleteMapping도 가능
  // public ResponseEntity<Void> delete(@RequestParam("id") int id) {
  //   boardService.delete(id);

  //   return ResponseEntity.noContent().build();
  // }

  @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE) 
  public ResponseEntity<Void> delete(@PathVariable int id) { // http://localhost:8080/api/board/delete/10 이런식으로 한단계로 나눠서 파라미터를 받는방식
    boardService.delete(id);

    return ResponseEntity.noContent().build();
  }

  // 게시물 수정
  @RequestMapping(value = "/update/{id}", method=RequestMethod.PUT)
  public ResponseEntity<Board> update(@PathVariable int id, @RequestBody Board updatedBoard) {
    try {
      Board board = boardService.update(id, updatedBoard.getWriter(), updatedBoard.getTitle(), updatedBoard.getContent());
      return ResponseEntity.ok(board);
    } catch (RuntimeException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
