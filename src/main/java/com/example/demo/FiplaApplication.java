package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.example.controller")
@SpringBootApplication
public class FiplaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiplaApplication.class, args);
	}

}
