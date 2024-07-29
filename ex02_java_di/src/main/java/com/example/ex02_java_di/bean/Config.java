package com.example.ex02_java_di.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 여러 bean을 등록하는 클래스에 사용하는 애노테이션
public class Config {
  
  @Bean // 메소드를 bean으로 등록해 관리하게끔 해줌
  public Member member1() {
    Member member1 = new Member();
    member1.setName("김일남");
    member1.setPrinter(new PrinterA());
    return member1;
  }

  @Bean(name="kim2") // bean 이름을 변경 (기본은 메소드명으로 됨.)
  public Member member2() {
    return new Member("김이남", new PrinterA());
  }

  @Bean
  public PrinterA printerA() {
    return new PrinterA();
  }

  @Bean
  public PrinterB printerB() {
    return new PrinterB();
  }
}
