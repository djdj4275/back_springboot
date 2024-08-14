package com.example.ex16_jpa_board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ex16_jpa_board.service.BoardService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MyController {

  @Autowired
  BoardService boardService;
  
  // default 경로로 접근시 "/list"에 맞는곳으로 리다이렉트시킴
  @RequestMapping("/")
  public String root() throws Exception {
    return "redirect:list";
  }

  @RequestMapping("/list")
  public String userlistPage(Model model) {
    model.addAttribute("list", boardService.list());
    return "list";
  }

  // @RequestMapping("/view")
  // public String view(HttpServletRequest request, Model model) {
  //   int id = Integer.parseInt(request.getParameter("id"));
  //   model.addAttribute("view", boardService.view(id));
  //   return "view";
  // }

  @RequestMapping("/view")
  public String view(@RequestParam("id") int id, Model model) {
    model.addAttribute("view", boardService.view(id));
    return "view";
  }

  @RequestMapping("/write-form")
  public String writeForm() {
    return "write-form";
  }

  @RequestMapping("/write")
  public String write(Model model, HttpServletRequest request) {
    boardService.write(request.getParameter("writer"), request.getParameter("title"), request.getParameter("content"));
    return "redirect:list";
  }

  @RequestMapping("/delete")
  public String delete(Model model, HttpServletRequest request) {
    boardService.delete(Integer.parseInt(request.getParameter("id")));
    return "redirect:list";
  }
}
