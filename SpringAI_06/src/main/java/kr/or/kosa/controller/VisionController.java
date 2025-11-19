package kr.or.kosa.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class VisionController {

	   //ChatClient AI 객체선언
		private final ChatClient chatClient;
		
		public VisionController(ChatClient.Builder Builder) {
			this.chatClient = Builder.build(); //open AI 연결한 객체의 주소
		}
		
		@GetMapping
		public String visionPage() {
			return "vision";
		}
		
		@PostMapping("/upload")
		@ResponseBody
		public ResponseEntity<Map<String, String>> vision(
													@RequestParam("file") MultipartFile file , 	
													@RequestParam("message") String message)
		{
			Map<String , String> response = new HashMap<>();
			
			try {
				  String useMessage = (message == null || message.isEmpty()) ? "이미지 분석해주" : message;
				  
				  //AI 요청
				  String result = chatClient.prompt()
						                    .user(userSpec -> userSpec.text(useMessage)
						                    .media(MimeType.valueOf(Objects.requireNonNull(file.getContentType())),file.getResource()))
						                    .call()
						                    .content();
				  response.put("result", result); //분석 결과
				  System.out.println("결과 result : " + response);
				  
				  return ResponseEntity.ok(response);
						                    
			} catch (MaxUploadSizeExceededException e) {
				  response.put("error", "업로드한 파일이 너무 커요 ....최대 10MB 가능");
				  return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(response);
			} catch(Exception e) {
				  response.put("error", "파일 업로드 중 오류 발생");
				  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
			}

		}
		
}

/*
 media(MimeType , Resource)
 이미지 타입 (image/png , image/jpeg) 
 타입을 알려주어야 AI 분석 가능 ....
 
 */