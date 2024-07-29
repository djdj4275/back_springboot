package com.example.ex03_annotation_di.bean;

import org.springframework.stereotype.Component;

@Component
public class PrinterA implements Printer {
  @Override
  public void print(String message) {
    System.out.println("PrinterA : " + message);
  }
}
