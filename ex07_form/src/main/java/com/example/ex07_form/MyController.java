package com.example.ex07_form;

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
    return "Form 데이터 전달";
  }

  // 클라이언트로 부터 값을 받아서 입력하도록 동적 적용 (도메인의 url에서 값 받아옴)
  // ex. http://localhost:8080/test2?id=user1&name=김일남
  @RequestMapping("/test1")
  public String test1(HttpServletRequest httpServletRequest, Model model) {
    String id = httpServletRequest.getParameter("id");
    String name = httpServletRequest.getParameter("name");

    model.addAttribute("id", id);
    model.addAttribute("name", name);
    return "test1";
  }

  // 위와 동일 (방식만 바꿈)
  @RequestMapping("/test2")
  public String test2(@RequestParam("id") String id, @RequestParam("name") String name, Model model) {
    model.addAttribute("id", id);
    model.addAttribute("name", name);
    return "test2";
  }

  // 위와 동일 (방식만 바꿈)
  @RequestMapping("/test3")
  // Member 클래스 안에 필드값을 파라미터값으로 쓰게끔 함
  public String test3(Member member, Model model) {
    return "test3";
  }

  // 위와 동일 (방식만 바꿈) 단, url 방식이 좀 다름
  // ex) http://localhost:8080/test4/user1/김일남
  @RequestMapping("/test4/{id}/{name}")
  public String test4(@PathVariable String id, @PathVariable String name, Model model) {
    model.addAttribute("id", id);
    model.addAttribute("name", name);
    return "test4";
  }
}
