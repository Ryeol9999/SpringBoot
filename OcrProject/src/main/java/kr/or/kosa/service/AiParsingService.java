package kr.or.kosa.service;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AiParsingService {

    private final OpenAiChatModel chatModel;

    public String convertToJson(String rawText) {

        String prompt = """
        아래는 OCR로 추출한 약봉지/조제약 안내문 텍스트입니다.
        이 텍스트를 기반으로 ERD에 저장 가능한 약 정보 JSON으로 변환하세요.

        ⭐ 반드시 아래 JSON 구조를 따르세요 ⭐

        {
          "user_id": 1,
          "medications": [
            {
              "name": "",
              "ingredient": "",
              "description": "",
              "dosage": "",
              "frequency": 0,
              "start_date": "",
              "end_date": "",
              "quantity": 0,
              "schedule": [
                { "time": "", "type": "" }
              ]
            }
          ]
        }

        📌 변환 규칙:

        1) **약 이름(name)**  
           OCR 텍스트에서 약품명(파목신, 오구멘틴, 종합감기약 등)을 추출해 입력.

        2) **ingredient (약 성분/분류)**  
           '페니실린계 항생제', '항히스타민제', '기관지확장제' 등 분류명을 사용.

        3) **description (약 설명)**  
           OCR 텍스트에서 약에 대한 설명 부분 그대로 입력.

        4) **dosage (복용량 원문)**  
           예: "0.5씩 3회 3일분" / "1씩 2회 3일분"

        5) **frequency (하루 복용 횟수)**  
           '3회', '2회' 등에서 숫자만 추출.

        6) **start_date / end_date**  
           처방일 = start_date  
           다음 내원일 = end_date  
           (OCR에서 찾을 수 없는 경우 비워두지 말고 status에 근거하여 유추)

        7) **quantity (총 복용 횟수)**  
           frequency × 총 일수  

        8) **schedule 자동 생성 규칙**  
           frequency 1 → 08:00 (MORNING)  
           frequency 2 → 08:00 (MORNING), 20:00 (EVENING)  
           frequency 3 → 08:00 (MORNING), 13:00 (LUNCH), 20:00 (EVENING)

        9) **약이 여러 개라면 medications 배열에 모두 포함**

        10) JSON은 형식을 유지하고 모든 문자열은 반드시 JSON 유효 형태로 반환.

        OCR 텍스트:
        """ + rawText;

        return chatModel.call(prompt);
    }
}
