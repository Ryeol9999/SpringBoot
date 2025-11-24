package kr.or.kosa.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmbedingService {
	
	private final VectorStore vectorStore;

	
	public void processUploadPdf(MultipartFile file) throws IOException{
		
		
		File tempFile = File.createTempFile("upload", "pdf");
		file.transferTo(tempFile);
		
		Resource fileResource = new FileSystemResource(tempFile);
		
		try {
			
			//PDF 문서 read 형식 정의   .... 환경정의 (read 정의)
			PdfDocumentReaderConfig config = PdfDocumentReaderConfig.builder()
																	.withPageTopMargin(0) //PDF 페이지 상단의 여백 결정
																	.withPageExtractedTextFormatter(  //페이지에서 추출된 텍스트 포매팅 방식
																		ExtractedTextFormatter.builder()
																						  	  .withNumberOfBottomTextLinesToDelete(0)
																						      .build())
																	.withPagesPerDocument(1) //한번에 처리할 페이지를 지정
																	.build();
			PagePdfDocumentReader pdfDocumentReader =new PagePdfDocumentReader(fileResource,config);
																				//임시파일     read옵션
			List<Document> documents = pdfDocumentReader.get();
			
			
			//벡터화 (float 배열 생성)
			TokenTextSplitter splitter = new TokenTextSplitter(1000, 400, 10, 5000, true);
			List<Document> spDocuments = splitter.apply(documents);
			
			//PGVector store 저장
			vectorStore.accept(spDocuments);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			tempFile.delete();
		}
	}
}
