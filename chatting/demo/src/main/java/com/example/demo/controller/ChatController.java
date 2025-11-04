package com.example.demo.controller;

import com.example.demo.domain.ChatMessage;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate; // 메시지 전송용
    private final ChatService chatService; // DB 저장 서비스

    /**
     * 클라이언트가 "/pub/chat/message" 로 메시지를 보내면 실행됨
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {

        // 1️⃣ DB 저장
        chatService.saveMessage(message);

        // 2️⃣ 로그 출력
        log.info("[채팅방 {}] {} : {}", message.getRoomId(), message.getSender(), message.getMessage());

        // 3️⃣ 구독자들에게 메시지 전송
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}
