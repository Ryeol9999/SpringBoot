package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트가 연결할 엔드포인트
        registry.addEndpoint("/ws/chat")
                .setAllowedOriginPatterns("*") // CORS 허용
                .withSockJS(); // SockJS fallback 지원
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 메시지 전송 경로
        registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트 -> 서버
        registry.enableSimpleBroker("/sub");                // 서버 -> 클라이언트
    }
}
