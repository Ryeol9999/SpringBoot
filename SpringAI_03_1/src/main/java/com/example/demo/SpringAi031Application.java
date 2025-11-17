package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.service.OllamaChatService;

@SpringBootApplication
public class SpringAi031Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringAi031Application.class, args);
	
		OllamaChatService ollamaChatService = context.getBean(OllamaChatService.class);
		String response = ollamaChatService.getQuestion();
		System.out.println("결과 : \n" + reponse);
	}

}
