package com.example.ex15_jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ex15_jpa.service.MyUserService;


@Controller
public class MyController {
    
    @Autowired
    private MyUserService myUserService;

    @RequestMapping("/")
    public @ResponseBody String root() throws Exception {
        return "JPA 사용하기";
    }

    // @RequestMapping(value = "/user", method = RequestMethod.GET)
    @GetMapping(value = "/user")
    public String userlistPage(Model model) {
        model.addAttribute("users", myUserService.list());
        return "userlist";
    }
}
