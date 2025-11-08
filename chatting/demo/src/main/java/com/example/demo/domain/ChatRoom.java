package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private Long roomId;          // AUTO_INCREMENT PK
    private String roomName;      // 방 이름
    private Integer enterPassword;  // ✅ 숫자형 입장 비밀번호
    private Integer deletePassword; // ✅ 숫자형 삭제 비밀번호
    private LocalDateTime createdAt; // 생성 시간
}
