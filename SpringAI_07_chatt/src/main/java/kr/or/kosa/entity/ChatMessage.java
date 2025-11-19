package kr.or.kosa.entity;

import java.time.LocalDateTime;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Entity (테이블 과 동일하게) > 테이블 만들고 ....

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;  //식별값
	
	private String userId; //사용자ID
	private String content; //메시지
	private boolean isUser; // true > 사용자 , false  > AI
	@Column(name="created_at")
	private LocalDateTime createdAt; //생성일
	
	
	public ChatMessage(String userId , String content , boolean isUser) {
		super();
		this.userId = userId;
		this.content = content;
		this.isUser = isUser;
		this.createdAt = LocalDateTime.now();
	}
}