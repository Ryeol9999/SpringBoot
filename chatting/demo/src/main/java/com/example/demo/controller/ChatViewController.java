package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatViewController {

    @GetMapping("/chat")
    public String chatPage() {
        // templates/chat.html을 렌더링함
        return "chat";
    }
}
