package com.example.ex05_thymeleaf_use;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
    
    @RequestMapping("/")
    public @ResponseBody String root() throws Exception {
        return "Thymeleaf in Gradle";
    }

    @RequestMapping("/test1")
    public String test1(Model model) {
        model.addAttribute("message", "Hello World");
        return "test1";
    }

    @RequestMapping("/test2")
    public String test2(Model model) {
        model.addAttribute("message", "Hello World (sub)");
        return "test2";
    }
}
