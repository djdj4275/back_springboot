package com.example.ex03_annotation_di.bean;

import org.springframework.stereotype.Component;

@Component("printerA")
public class PrinterA implements Printer{
    @Override
    public void print(String message) {
        System.out.println("Printer A : " + message);
    }
}
