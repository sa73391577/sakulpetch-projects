package com.example.thaiaddressbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ThaiaddressbatchApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ThaiaddressbatchApplication.class, args);
		int exitCode = SpringApplication.exit(context);
		System.exit(exitCode);
	}

}
