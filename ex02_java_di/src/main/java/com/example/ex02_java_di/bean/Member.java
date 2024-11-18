package com.example.ex02_java_di.bean;

public class Member {
    private String name;
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
