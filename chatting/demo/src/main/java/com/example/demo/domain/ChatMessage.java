package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private Long id;             // 메시지 번호 (PK)
    private Long roomId;         // 채팅방 ID (FK)
    private String sender;       // 보낸 사람
    private String message;      // 내용
    private LocalDateTime sendTime; // 전송 시간
}
