package com.example.ssb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

  @GetMapping("/sbb")
  @ResponseBody
  public String index() {
    System.out.println("index");
    return "스프링 부트 게시판";
  }
}
