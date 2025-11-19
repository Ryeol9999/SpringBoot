package kr.or.kosa.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

//user or AI

@Data
@AllArgsConstructor
public class ChatMessageDto {
	private String sender; //user or ai
	private String text;
}
