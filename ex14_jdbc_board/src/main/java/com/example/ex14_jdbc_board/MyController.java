package com.example.ex14_jdbc_board;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
  
  // default 경로로 접근시 "/list"에 맞는곳으로 리다이렉트시킴
  @RequestMapping("/")
  public String root() throws Exception {
    return "redirect:list";
  }

  @RequestMapping("/list")
  public String userlistPage(Model model) {
    return "list";
  }
}
