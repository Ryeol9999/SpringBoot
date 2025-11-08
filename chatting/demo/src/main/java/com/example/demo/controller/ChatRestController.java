package com.example.demo.controller;

import com.example.demo.domain.ChatMessage;
import com.example.demo.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatRestController {

    private final ChatMapper chatMapper;

    // ✅ 전체 메시지 조회
    @GetMapping("/messages")
    public List<ChatMessage> getAllMessages() {
        return chatMapper.findAll();
    }

    // ✅ 특정 채팅방 메시지 조회
    @GetMapping("/messages/{roomId}")
    public List<ChatMessage> getRoomMessages(@PathVariable Long roomId) {
        return chatMapper.findByRoomId(roomId);
    }

    // ✅ 메시지 수동 저장 테스트용 (Postman 확인용)
    @PostMapping("/send")
    public String sendMessage(@RequestBody ChatMessage message) {
        chatMapper.insertMessage(message);
        return "Message saved!";
    }
}
