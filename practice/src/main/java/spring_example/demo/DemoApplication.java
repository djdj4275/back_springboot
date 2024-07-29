package spring_example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// SpringApplicationBuilder를 사용하여 config 위치를 지정
		SpringApplication.run(new Class[] { DemoApplication.class }, new String[] {"--spring.config.location=classpath:/const/application.properties"});
	}

}
