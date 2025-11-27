package kr.or.kosa.controller;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.PromptUserSpec;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.kosa.service.HotelService;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hotel")
@CrossOrigin(origins= "http://localhost:5173/")
public class HotelController {
	
	private final HotelService hotelService;
	private final DocumentUploadController documentUploadController;
	
	private final VectorStore vectorStore;
	private final ChatModel chatModel;
	
	/*
	  MediaType.TEXT_EVENT_STREAM_VALUE : 
	   public static final String TEXT_EVENT_STREAM_VALUE = "text/event-stream"; 
	   이것이 의미하는 것: SSE(Server-Sent Events) 전송 타입
     이 MIME 타입은 클라이언트가 끊기지 않는 HTTP 연결을 통해 실시간으로 데이터를 받겠다는 의미입니다.
	  
	  
	  MediaType.APPLICATION_NDJSON_VALUE :
	   public static final String APPLICATION_NDJSON_VALUE = "application/x-ndjson";
	   NDJSON(Newline Delimited JSON) 형식이 뭐지?
     JSON 객체 여러 개를 연속으로 전송하지만, 배열 형태가 아니라 줄바꿈으로 구분하는 방식

		예시:
		{"message":"첫 번째 응답"}
		{"message":"두 번째 응답"}
		{"message":"세 번째 응답"}
	  
	  
	  MediaType.TEXT_PLAIN_VALUE : 그냥 텍스트로 흘려보냄
	  public static final String TEXT_PLAIN_VALUE = "text/plain";
	  순수 텍스트(String)를 그대로 보내고 싶을 때
		HTML 아님
		JSON 아님
		XML 아님
		SSE 아님
		파일 아님
		그냥 “문자열만” 전송
	  
	  
	 ChatModel을 사용해 직접 응답 생성
	 return chatModel.stream(template
          .replace("{context}", results.toString())
          .replace("{question}", question));
	 */ 
	@GetMapping(value="/chat", produces =MediaType.TEXT_PLAIN_VALUE)
	public Flux<String> hotelChatbot(@RequestParam("question") String question){
		
		ChatClient chatClient = ChatClient.builder(chatModel).build();
		//llm
		
		List<Document> results =vectorStore.similaritySearch(SearchRequest.builder()
																		  .query(question)
																		  .similarityThreshold(0.5)
																		  .topK(1)
																		  .build());
		System.out.println("Vectorstore 유사도 검색 결과  : " + results);
		
		//
		
		String template = """
				당신은 어느 호텔의 직원입니다. 문맥에 따라서 고객의 질문에 정중하게 답변해주세요.
				컨텍스트가 질문에 대답할 수 없는 경우, "죄송합니다. 모르겠습니다."라고 대답하세요.
				컨텍스트:
				{context}
				질문:
				{question}
				""";
		return chatClient.prompt().user(PromptUserSpec ->PromptUserSpec.text(template)
																	   .param("context", results)
																	   .param("question",question))
																	   .stream()
																	   .content();
	}

}
