package com.example.ex15_jpa_error_controll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ex15_jpa_error_controll.service.MyUserService;

import lombok.extern.slf4j.Slf4j;

@Controller
public class MyController {

  @Autowired
  private MyUserService myUserService;

  @RequestMapping("/")
  public @ResponseBody String root() throws Exception {
    return "JPA & Error Controll 사용하기";
  }
  
  @RequestMapping(value = "/user", method = RequestMethod.GET) // GetMapping(value="/user") 와 동일
  public String userlistPage(Model model) {
    model.addAttribute("users", myUserService.list());
    return "userlist";
  }
}
