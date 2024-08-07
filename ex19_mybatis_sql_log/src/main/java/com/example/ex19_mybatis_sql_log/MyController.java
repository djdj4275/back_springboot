package com.example.ex19_mybatis_sql_log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ex19_mybatis_sql_log.jdbc.MyUserDAO;

@Controller
public class MyController {

  @Autowired
  private MyUserDAO myUserDAO;

  @RequestMapping("/")
  public @ResponseBody String root() throws Exception {
    return "Mybatis 사용하기";
  }

  // http메소드의 종류
  // GET : 조회
  // POST : 데이터삽입
  // PUT : 전체수정
  // PATCH : 부분수정
  // DELETE : 삭제
  // HEAD, CONNECT, OPTIONS, TRACE 등등

  // 유저가 /user로 접근하면 DAO(레파지토리)의 list 메소드 실행시켜 db에서 값 들고오고
  // 그 값을 model 객체에 "users" 라는 값으로 넣어서 "userlist" html 렌더링시킴
  @RequestMapping(value = "/user", method = RequestMethod.GET) // GetMapping(value="/user") 와 동일
  public String userlistPage(Model model) {
    model.addAttribute("users", myUserDAO.list());
    return "userlist";
  }
}
