package kr.or.kosa.service;

import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OcrService {
	 public String extractText(MultipartFile file) throws Exception {

	        // 업로드된 파일 → ByteString 변환
	        ByteString imgBytes = ByteString.copyFrom(file.getBytes());

	        // Vision API 요청 준비
	        Image img = Image.newBuilder().setContent(imgBytes).build();
	        Feature feature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();

	        AnnotateImageRequest request =
	                AnnotateImageRequest.newBuilder()
	                        .addFeatures(feature)
	                        .setImage(img)
	                        .build();

	        try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {

	            BatchAnnotateImagesResponse response =
	                    client.batchAnnotateImages(List.of(request));

	            AnnotateImageResponse res = response.getResponses(0);

	            if (res.hasError()) {
	                return "OCR Error: " + res.getError().getMessage();
	            }

	            return res.getFullTextAnnotation().getText();
	        }
	    }
	}