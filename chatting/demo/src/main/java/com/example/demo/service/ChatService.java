package com.example.demo.service;

import com.example.demo.domain.ChatMessage;
import com.example.demo.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMapper chatMapper;

    // 전체 메시지 조회
    public List<ChatMessage> getAllMessages() {
        return chatMapper.findAll();
    }

    // 특정 채팅방 메시지 조회
    public List<ChatMessage> getMessagesByRoomId(Long roomId) {
        return chatMapper.findByRoomId(roomId);
    }

    // 메시지 저장
    public void saveMessage(ChatMessage message) {
        chatMapper.insertMessage(message);
    }
}
