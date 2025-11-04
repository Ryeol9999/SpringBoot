package com.example.demo.mapper;

import com.example.demo.domain.ChatMessage;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ChatMapper {

    // 모든 메시지 조회
    List<ChatMessage> findAll();

    // 특정 방 메시지 조회
    List<ChatMessage> findByRoomId(String roomId);

    // 메시지 저장
    int insertMessage(ChatMessage message);
}
