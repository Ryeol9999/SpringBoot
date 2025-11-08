package com.example.demo.controller;

import com.example.demo.domain.ChatRoom;
import com.example.demo.mapper.ChatMapper;
import com.example.demo.mapper.ChatRoomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomMapper roomMapper;

    private final ChatMapper chatMapper;

    // ✅ 모든 방 목록 조회
    @GetMapping("/rooms")
    public List<ChatRoom> getRooms() {
        return roomMapper.findAll();
    }

    // ✅ 특정 방 조회
    @GetMapping("/rooms/{roomId}")
    public ChatRoom getRoom(@PathVariable Long roomId) {
        return roomMapper.findById(roomId);
    }

    // ✅ 방 생성
    @PostMapping("/rooms")
    public String createRoom(@RequestBody ChatRoom room) {
        roomMapper.insertRoom(room);
        return "✅ 방이 생성되었습니다. roomId=" + room.getRoomId();
    }

    // ✅ 방 삭제 (삭제 비밀번호 숫자 비교)
    @DeleteMapping("/rooms/{roomId}")
    public String deleteRoom(@PathVariable Long roomId, @RequestParam Integer password) {
        ChatRoom room = roomMapper.findById(roomId);
        if (room == null) return "❌ 존재하지 않는 방입니다.";
        if (!Objects.equals(room.getDeletePassword(), password)) return "❌ 삭제 비밀번호가 일치하지 않습니다.";

        // ✅ 1. 메시지 먼저 삭제
        chatMapper.deleteMessagesByRoomId(roomId);

        // ✅ 2. 방 삭제
        roomMapper.deleteRoom(roomId);

        return "✅ 채팅방이 삭제되었습니다.";
    }

    @PostMapping("/rooms/{roomId}/check-enter")
    public String checkEnterPassword(
            @PathVariable Long roomId,
            @RequestParam Integer password
    ) {
        ChatRoom room = roomMapper.findById(roomId);

        if (room == null) {
            return "❌ 존재하지 않는 방입니다.";
        }

        if (Objects.equals(room.getEnterPassword(), password)) {
            return "✅ 입장 비밀번호 일치 — 입장 가능";
        } else {
            return "❌ 입장 비밀번호가 일치하지 않습니다.";
        }
    }
}

