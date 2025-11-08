package com.example.demo.mapper;

import com.example.demo.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatRoomMapper {
    List<ChatRoom> findAll();          // 모든 방 조회
    ChatRoom findById(Long roomId);    // 특정 방 조회
    int insertRoom(ChatRoom room);     // 방 생성
    int deleteRoom(Long roomId);       // 방 삭제
}
