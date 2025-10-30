package com.example.demo.config;

import java.lang.management.ManagementFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //객체 생성 .... 주입 자바파일 
@EnableWebSecurity
public class SecurityConfig {
	/*
	 인증과 권한
	 1. in memory
	 2. jdbc (2가지 쿼리 : id, pwd, enabled : auth)
	 3. mybatis(jpa) : 사용자 정의 UserDetailService 재정의 
	 
	 */
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		//인증 , 권한, 로그인 방식 정의(spring 제공, 사용자 별도의 커스터마이징 가능)
		
		//폼 로그인 방식 사용하지 않아요(x)
		//form > id, pwd > 전송 > 받아서 ......
		
		//문법 람다식으로 사용 (6.x.x)
		
		//http.formLogin().disable();  //5.x.x  method 체인방식 문제 x
		http.formLogin((login) -> login.disable());  //시대의 흐름 (람다 DSL 표기)
		
		// http spring security 제공 자동 비활성화
		http.httpBasic((basic) -> basic.disable());
		
		//CSRF (hidden 태그에 암호화된 문자열 -> 위변조 방지 -> 비활성화)
		http.csrf((csrf) -> csrf.disable());
		
		//JWT 사용 -> session 통한 정보를 관리하지 않을거야 -> 비활성화
		
		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}
}
