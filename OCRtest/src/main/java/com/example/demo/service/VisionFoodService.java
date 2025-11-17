package com.example.demo.service;

import java.util.Base64;

import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VisionFoodService {

    private final OpenAiImageModel openAiImageModel;

    public String detectFood(byte[] imageBytes) {

        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        String prompt = """
                이 사진 속 음식이 무엇인지 알려줘.
                가능한 경우:
                - 음식 이름
                - 유사한 음식 후보
                - 대표 재료
                - 간단한 설명
                
                JSON으로 답해:
                {
                  "name": "",
                  "ingredients": [],
                  "description": ""
                }
                """;

        var response = openAiImageModel.call(
                new OpenAiImageModel.ImagePrompt(
                        prompt,
                        base64Image,
                        OpenAiImageOptions.builder()
                                .withModel("gpt-4o-mini") // 시각 모델
                                .build()
                )
        );

        return response.getResult().getOutputText();
    }
}
