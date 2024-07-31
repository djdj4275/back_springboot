package com.example.ex11_validation_initbinder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

@Controller
public class MyController {
  
  @RequestMapping("/")
  public @ResponseBody String root() {
    return "Validation (1)";
  }

  // @RequestMapping("/insert-form")
  // public String insert1(Model model) {
  //   model.addAttribute("dto", new ContentDTO());
  //   return "createPage";
  // }

  // 위와 동일하지만 @ModelAttribute 어노테이션 이용한 방법
  @RequestMapping("/insert-form")
  public String insert1(@ModelAttribute("dto") ContentDTO contentDTO) {
    return "createPage";
  }

  @RequestMapping("/create")
  // createPage.html에서 전송받은 dto를 매개변수로 받음
  public String insert2(@ModelAttribute("dto") @Valid ContentDTO contentDTO, BindingResult result) {
    String page = "createDonePage"; 

    // 강한결합
    // ContentValidator validator = new ContentValidator();
    // validator.validate(contentDTO, result);

    // 약한결합 (매개변수에 @Valid 필요)
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ContentValidator());
    }

    // 에러메세지 확인
    System.out.println("validation result : " + result.hasErrors());

    // 에러가 있다면 (유효성검사에서 걸리면) 원래 페이지로 되돌림
    if(result.hasErrors()) {
      page = "createPage";
    }

    return page;
  }
}
