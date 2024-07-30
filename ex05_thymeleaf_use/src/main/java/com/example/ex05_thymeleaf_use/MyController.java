package com.example.ex05_thymeleaf_use;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
  
  @RequestMapping("/")
  @ResponseBody
  public String root() throws Exception {
    return "Thymeleaf in Gradle";
  }

  @RequestMapping("/test1")
  public String test1(Model model) { // 데이터 전달을 위해 model 객체 사용
    // message란 이름으로 값 넘김
    model.addAttribute("message", "Hello world");
    return "test1"; // test1 html파일을 리턴
  }

  @RequestMapping("/test2")
  public String test2(Model model) {
    model.addAttribute("message", "Hello world (sub)");
    return "test2"; // test2 html파일을 리턴
  }
}
