package kr.or.kosa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
@Service
public class CustomerSupportService {
	
	
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
			
			내부에 없는 정보일 경우, 정중히 "죄송합니다 제품이 없습니다"를 답변해주세요
			
			""";
	public String getChatResponse(String userId, String userMessage) {
		
		//Map userId 해당하는 대화 기록이 존재 하는지 확인 
		List<Message> history = chatHistory.computeIfAbsent(userId, k -> new ArrayList<>());
		
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
		conversion.addAll(history);  //특정 사횽자의 목록 (kim씨의 모든 대화목록)
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
		
		if(response == null || response.getResult()== null || response.getResult().getOutput() == null) {
			System.err.println("AI 응답이 유효하지 않습니다");
			return "현재 AI 가 네트워크 문제로 답을 할수 없습니다. ";
		}
		
		//다시 기록에 대한 현재 질문과 답변을 history에 추가
		//history
		history.add(new UserMessage(userMessage)); //user가 질문한 내용
		history.add(response.getResult().getOutput()); //AI가 응답한 내용
		
		// 대화가 너무 길어져서
		// 가장 단순한 방법 (배열길이 ...)
		if(history.size() > 10) {
			history.subList(0, history.size()-10).clear();
		
		}
		//대화기록이 10개보다 많아지면 10개남기고 오래된 메세지부터 삭제
		//최근 대화 10개만 기억하기
		
		System.out.println("[ 사용자 : " +userId + " 대화 기록 ]");

		System.out.println("================================");
		
		//history > map > key 던져서 > userId
		List<Message> messages = chatHistory.get(userId);
		
		for(int i = 0 ; i < messages.size() ; i += 2) {
			Message question = messages.get(i);
			 System.out.println("Question : " +question.getText());
			
			 if(i + 1 <messages.size()) {
				 Message answer = messages.get(i+1);
				 System.out.println("Answer +" + answer.getText());
			 }
			 
			 System.out.println("================================");
		}
		return response.getResult().getOutput().getText();
	}
	/*
	 SystemMessage "system"            모델에게 행동 지침/컨텍스트를 주는 설정
    
    UserMessage   "user"              사람이 입력한 질문, 요청

    AssistantMessage "assistant"       모델이 생성한 응답 
	 
	  
	 */
}
