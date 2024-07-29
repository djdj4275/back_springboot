package com.example.ex03_annotation_di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Member {

  @Value("김일남")
  private String name;

  @Autowired // 객체 주입 어노테이션
  @Qualifier("printerA") // printerA, printerB 중에 확실하게 정해줌
  private Printer printer;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Printer getPrinter() {
    return this.printer;
  }

  public void setPrinter(Printer printer) {
    this.printer = printer;
  }

  public Member() {}

  public Member(String name, Printer printer) {
    this.name = name;
    this.printer = printer;
  }

  public void print() {
    printer.print("hello " + name + "!");
  }
}
