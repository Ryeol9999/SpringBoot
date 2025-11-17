package com.example.demo.controller;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ApiController {
	private final OpenAiChatModel chatModel;
	
	@GetMapping
	public String getChat(@RequestParam("message") String message) {
		return chatModel.call(message);
	}

}
