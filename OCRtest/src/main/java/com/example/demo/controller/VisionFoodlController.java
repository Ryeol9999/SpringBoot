package com.example.demo.controller;

import com.example.demo.service.VisionFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vision")
public class VisionFoodController {

    private final VisionFoodService visionFoodService;

    @PostMapping("/food")
    public String detectFood(@RequestParam("image") MultipartFile file) throws Exception {

        byte[] bytes = file.getBytes();

        String result = visionFoodService.detectFood(bytes);

        return result;
    }
}
