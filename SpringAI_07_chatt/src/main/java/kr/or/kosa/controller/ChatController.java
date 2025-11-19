package kr.or.kosa.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.dto.ChatRequestDto;
import kr.or.kosa.dto.ChatResponseDto;
import kr.or.kosa.service.CustomerSupportService;

@RestController
public class ChatController {
	
	private CustomerSupportService service;
	
	public ChatController(CustomerSupportService service) {
		this.service =service;
	}
	
	@PostMapping("/chat")
	public ChatResponseDto chat(@RequestBody ChatRequestDto request) {
		
		ChatResponseDto responseMessage = service.getChatResponse(request.getUserId(), request.getMessage());
		
		return responseMessage;
		}
}
