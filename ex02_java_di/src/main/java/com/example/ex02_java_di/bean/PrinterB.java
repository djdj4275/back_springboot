package com.example.ex02_java_di.bean;

public class PrinterB implements Printer{
    @Override
    public void print(String message) {
        System.out.println("Printer B : " + message);
    }
}
