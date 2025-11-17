package kr.or.kosa.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.or.kosa.service.AiParsingService;
import kr.or.kosa.service.OcrService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ocr")
public class OcrController {

    private final OcrService ocrService;
    private final AiParsingService aiParsingService;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws Exception {

        // 1) OCR 텍스트 뽑기
        String ocrText = ocrService.extractText(file);

        // 2) OpenAI를 이용해 JSON 생성
        String json = aiParsingService.convertToJson(ocrText);

        // 3) 그대로 반환
        return ResponseEntity.ok(json);
    }
}