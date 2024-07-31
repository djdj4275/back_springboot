package com.example.ex08_lombok;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyController {
  
  @RequestMapping("/")
  @ResponseBody
  public String root() throws Exception {
    return "Lombok 사용하기";
  }

  // 위와 동일 (방식만 바꿈)
  @RequestMapping("/test3")
  // Member 클래스 안에 필드값을 파라미터값으로 쓰게끔 함
  public String test3(Member member, Model model) {
    return "test3";
  }
}
