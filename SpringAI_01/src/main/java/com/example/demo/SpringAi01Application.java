package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.service.ChatService;

@SpringBootApplication
public class SpringAi01Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringAi01Application.class, args);
		ChatService chatService = context.getBean(ChatService.class);
		chatService.run();
	}

}
