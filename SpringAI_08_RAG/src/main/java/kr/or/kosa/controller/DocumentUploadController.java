package kr.or.kosa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.kosa.service.EmbedingService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class DocumentUploadController {
	
	private final ChatModel chatModel;
	private final EmbedingService embedingService;
	private final VectorStore vectorStore;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadPdf(@RequestParam("file") MultipartFile file){
		try {
			embedingService.processUploadPdf(file);
			return ResponseEntity.ok("PDF 파일 업로드 임베딩 처리 완료");
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("오류 : " +e.getMessage());
		}
	}
	//LLM 질의 > 백터 데이터 참조 > 유사도 > 기반으로 > LLM 질의 .. 결과
	
	
	private String promptTempate = """
			다음 문서를 참고하여 질문에 대해 답변해 주세요.
			문서에서 답을 찾을 수 없다면, "관련 정보를 찾을 수 없습니다." 라고 답변해 주세요
			
			[문서]
			{context}
			
			[질문]
			{question}
			
			""";
	//파일로 만들고 싶다 > 안내.st 파일 생성
	@PostMapping("/rag")
	public String ragChat(@RequestParam("question") String question) {
		
		PromptTemplate template = new PromptTemplate(promptTempate);
		
		Map<String , Object> promptParameters = new HashMap<>();
		promptParameters.put("question", question);
		promptParameters.put("context", "");
		
		//1. VectorStore 에서 유사도 높은 문서 n 개 검색
		List<Document> similarityDocuments = vectorStore.similaritySearch(SearchRequest.builder()
																					   .query(question)
																					   .topK(2)
																					   .build());
		//2. 검색된 문서 내용을 하나의 문자열 결합 출력
		String documents = similarityDocuments.stream()
											  .map(document -> document.getFormattedContent().toString())
											  .collect(Collectors.joining("\n"));
		
		promptParameters.put("context", documents);
		//3 . 최종적으로 질의하고 유사도 높은 문장 결합해서 LLM에게 질의
		
		return chatModel.call(template.create(promptParameters)).getResult().getOutput().getText();
	}



}
