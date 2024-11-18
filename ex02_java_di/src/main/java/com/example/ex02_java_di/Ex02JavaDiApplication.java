package com.example.ex02_java_di;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.ex02_java_di.bean.Config;
import com.example.ex02_java_di.bean.Member;
import com.example.ex02_java_di.bean.Printer;

// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication
public class Ex02JavaDiApplication {

	public static void main(String[] args) {
		// SpringApplication.run(Ex02JavaDiApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

		Member member1 = (Member) context.getBean("member1");
		member1.print();

		Member member2 = context.getBean("kim2", Member.class);
		member2.print();

		Printer printer = context.getBean("printerB", Printer.class);
		member1.setPrinter(printer);
		member1.print();
	}

}
