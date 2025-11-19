package kr.or.kosa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import kr.or.kosa.dto.ChatMessageDto;
import kr.or.kosa.dto.ChatResponseDto;
import kr.or.kosa.entity.ChatMessage;
import kr.or.kosa.repository.ChatMessageRepository;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
@Service
public class CustomerSupportService {
	
	//의존성 주입
	@Autowired
	private ChatMessageRepository chatMessageRepository;
	
	
	//chatClient AI 객체선언
	
	private final ChatClient chatClient;
	
	
	public CustomerSupportService(ChatClient.Builder chatclientBuilder) {
		this.chatClient = chatclientBuilder.build();  //open AI 연결한 객체의 주소
		
	}
	
	//챗봇과의 대화 내용 (기록을 저장하고 싶어요)
	private final Map<String, List<Message>> chatHistory =
			new ConcurrentHashMap<String, List<Message>>();

	//String Hong [ new Message() ][ new Message() ][ new Message() ]
	
	//챗봇 시스템 프롬프트
	
	private final String systemPrompt = """
			당신은 "koda Tech"라는 회사의 E-커머스 고객 지원 챗봇입니다.
			항상 친절하고 명확하게 답변해야 합니다. 사용자가 상품에 대해서 물으면 아래 정보를 기반으로 답해주세요.
			
			- 상품명: 갤럭시 AI 북
			- 가격: 1,500,000원
			- 특징: 최신 AI 기능이 탑재된 고성능 노트북, 가볍고 배터리가 오래간다
			- 재고: 현재 구매 가능
			
			- 상품명: AI 스마트 워치
			- 가격: 350,000원
			- 특징: 건강 모니터링이 가능하고, 스마트폰과 연동 기능을 제공. 방수 기능을 포함
			- 재고: 품절(5일 후 재입고 예정)
			
			내부에 없는 정보 일 경우, 정중하게 안내하면서도 일반적인 정보나 유사한 내용을 최대한 제공해 주세요
			""";
	//내부에 없는 정보 일 경우, 정중하게 안내하면서도 일반적인 정보나 유사한 내용을 최대한 제공해 주세요
			//내부에 없는 정보일 경우, 정중히 "죄송합니다 제품이 없습니다"를 답변해주세요
	
	public ChatResponseDto getChatResponse(String userId, String userMessage) {
		
		//이전 대화내용을 가져오기 특정 사용자에 대해서
		List<ChatMessage> previousMessages = chatMessageRepository.findByUserIdOrderByCreatedAtAsc(userId);
		
		//이전 대화 이력을 (stream) 변환 해서 사용
		
		//MEssage (인터페이스) > 구현 >
		List<Message> history = previousMessages.stream()
									      		   .map(cm -> cm.isUser() ?  new UserMessage(cm.getContent())
																		   : new AssistantMessage(cm.getContent()))
												   .collect(Collectors.toList());
	
		//Map userId 해당하는 대화 기록이 존재 하는지 확인 
		// List<Message> history = chatHistory.computeIfAbsent(userId, k -> new ArrayList<>());
		
		/*
			chatHistory는 Map<String, List<Message>> 형태(보통)로,
			각 사용자(userId)의 이전 대화 목록(List<Message>)을 저장하고 있습니다.

			computeIfAbsent()는 map에 값이 없으면 자동으로 생성하는 메서드입니다.

 			즉, userId의 대화 기록이 없으면 새로 ArrayList()를 만들고, 있으면 기존 기록을 꺼냄.
 			
 			hong > 기존 대화 내용이 있어 > 그럼 가지고 와
 			hong > 없으면 새로운 배열을 생성해
 			
 			k -> new ArrayList<>() > k는 userid
 		
 		*/
		
		// 시스템 프롬프트 (메세지) 와 사용자 메세지를 포함해서 전체 대화내용을 
		
		List<Message> conversion = new ArrayList<Message>();
		
		//1.system, 기존대화, 현재 message
		conversion.add(new SystemPromptTemplate(systemPrompt).createMessage());
		//DB 사용
		conversion.addAll(history);  //Map메모리가 아니라 h2,oracle. mysql 과거 대화기록 가져와서
		conversion.add(new UserMessage(userMessage));
		
		//1. systemMessage > 대화 리스트 > 현제 요청한 내용 다 conversion 안에 집어넣어버림
		
		//ChatClient 사용하여 OPEN AI 요청
		Prompt prompt= new Prompt(conversion);
		/*
		[
			SystemMessage,
			history : 과거의 내용들
			userMessage("AI 폰 어때")
		]
		
		예)
		systemPrompt = " 너는 여행 가이드야 "
		history = [
			UserMessage("북해도 여행 추천해줘")
			AssistantMessage("북해도에 얼음 축제가 볼만해 ")
		]
		userMessage ="맛집도 추천해줘";
		*/
		ChatResponse response =	chatClient.prompt(prompt).call().chatResponse();
		
		String aiResponseMEssage = response.getResult().getOutput().getText();
		
		//현재 질문과 답변을 DB저장
		chatMessageRepository.save(new ChatMessage(userId,userMessage,true));
		//홍길동 , 저 그림은 뭐야, true
		chatMessageRepository.save(new ChatMessage(userId,aiResponseMEssage,false));
		//홍길동, 그림은 정선 화가의 그림입니다. false(AI)
		
		
		
		//JSON 구조로 변경해서 메세지 리스트 생성
		List<ChatMessageDto> messages = new ArrayList<ChatMessageDto>();
		
		//이전데이터 추가
		for(ChatMessage msg: previousMessages) {
			String sender = msg.isUser() ? "user":"ai";
			String content = msg.getContent();
			
			messages.add(new ChatMessageDto(sender, content));
		}
		
		//현제 사용자 메세지
		messages.add(new ChatMessageDto("user", userMessage));
		//현재 ai 메세지
		messages.add(new ChatMessageDto("ai", aiResponseMEssage));
		
		return new ChatResponseDto(messages);
	/*
	 SystemMessage "system"            모델에게 행동 지침/컨텍스트를 주는 설정
    
    UserMessage   "user"              사람이 입력한 질문, 요청

    AssistantMessage "assistant"       모델이 생성한 응답 
	 
	  
	 */
	}
}
