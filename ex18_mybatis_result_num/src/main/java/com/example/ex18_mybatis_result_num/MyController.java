package com.example.ex18_mybatis_result_num;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ex18_mybatis_result_num.dao.BoardDAO;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.HashMap;

@Controller
public class MyController {

  @Autowired
  BoardDAO dao;
  
  // default 경로로 접근시 "/list"에 맞는곳으로 리다이렉트시킴
  @RequestMapping("/")
  public String root() throws Exception {
    return "redirect:list";
  }

  @RequestMapping("/list")
  public String userlistPage(Model model) {
    model.addAttribute("list", dao.listDAO());
    
    int totalCount = dao.articleCount();
    System.out.println("Count : " + totalCount);

    model.addAttribute("totalCount", totalCount);

    return "list";
  }

  @RequestMapping("/view")
  public String view(HttpServletRequest request, Model model) {
    String id = request.getParameter("id");
    model.addAttribute("view", dao.viewDAO(id));
    return "view";
  }

  @RequestMapping("/write-form")
  public String writeForm() {
    return "write-form";
  }

  @RequestMapping("/write")
  public String write(Model model, HttpServletRequest request) {
    String name = request.getParameter("writer");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    
    Map<String, String> map = new HashMap<>();

    map.put("item1", name);
    map.put("item2", title);
    map.put("item3", content);

    int result = dao.writeDAO(map); // db에 작성해서 넣는부분
    System.out.println(result);

    return "redirect:list";
  }

  @RequestMapping("/delete")
  public String delete(Model model, HttpServletRequest request) {
    dao.deleteDAO(request.getParameter("id"));
    return "redirect:list";
  }
}
