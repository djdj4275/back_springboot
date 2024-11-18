package com.example.ex03_annotation_di.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Member {

    @Value("김일남")
    private String name;

    @Autowired
    @Qualifier("printerA")
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
        printer.print("Hello " + name + "!");
    }
}
