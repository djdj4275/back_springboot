package com.example.ex03_annotation_di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
  @Autowired
  Member member1;

  @Autowired
  @Qualifier("printerB")
  Printer printer;

  @Autowired
  Member member2;

  @RequestMapping("/")
  @ResponseBody
  public String root() {

    // Autowired로 의존성이 주입되어 내부 메소드 사용가능
    // bean에서 적절한 객체를 매핑시켜준것
    member1.print(); 

    member1.setPrinter(printer);
    member1.print();

    if(member1 == member2) { // true (싱글톤이다.)
      System.out.println("동일한 객체");
    } else {
      System.out.println("서로 다른 객체");
    }

    return "어노테이션 사용하기";
  }
}
