package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.contants.SecurityConstants;
import com.example.demo.domain.AuthenticationRequest;
import com.example.demo.prop.JwtProps;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController //클래스 안에있는 모든 함수는 @Responsebody 를 가지고 있음 뷰를만들지않고 클라이언트에게 바로 데이터 전송가능
public class LoginController {
	
	@Autowired
	private JwtProps jwtProps;
	
	//1. 클라이언트 username, password 받아서 토큰 발급
	//2. JWT (header, payload, secret key)
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
		
		//POSTMAN 사용 : method > post, localhost:8090/login , raw(JSON) {"username":"hong","password":"1004"}
		
		String username = request.getUsername();
		String password = request.getPassword();
		
		log.info("username :" + username);
		log.info("password :" + password);
		
		//DB연결 .. select 확인.. 권한정보 .. 했다고 치고
		
		//role 
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		roles.add("ROLE_ADMIN");

		//payload (이름,권한)
		
		//재료준비
		//1. header
		//2. payload (username, roles)
		//3. secretkey 식별값 
		
		//토큰 생성
		//1.서명가지고오기
		String secretkey = jwtProps.getSecretkey();
		
		//서명이 사용될떼는 (토큰 byte[] 변환)
		byte[] signingkey = jwtProps.getSecretkey().getBytes(); //실제 토큰에 들어가는 값
		
		
		String jwt = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingkey),Jwts.SIG.HS512)
			    .header().add("typ",SecurityConstants.TOKEN_TYPE).and()
			    .expiration(new Date(System.currentTimeMillis() + 1000*60*60*24*1))
			    .claim("uid",username)
			    .claim("rol",roles)
			    .compact();
		log.info("jkt 완성본" + jwt);
		/*
		토큰 만들기
		1. Keys.hmacShaKeyFor(signingKey) 키 제공하기 
		2. Jwts.SIG.HS512  알고리즘 설정 정보 
		3. SecurityConstants.TOKEN_TYPE >> public static final String TOKEN_TYPE="JWT";
		4. expiration 소멸타임 (memory , file 쿠키) : file 쿠키 (소멸시간) ...session 에 의존하는 memory cookie
		   expiration(new Date( System.currentTimeMillis() + 1000*60*60*24*1))
		 
		 Access Token의 한계와 Refresh Token의 필요성 고민 ^^
		/*
			[ 기초 용어 ]
			
			클레임(Claim) 토큰 기반의 인증
			클레임이란 [[ 사용자 정보나 데이터 속성 ]]등을 의미한다. 
			그래서 [[ 클레임 토큰 이라고 하면 토큰 안에 사용자 정보나 데이터 속성들을 담고있는 토큰 ]]이라 생각하면 되고, 
			이런 클레임을 기반한 토큰 중 가장 대표적인 것이 JWT가 있다
			
			Bearer	토큰을 가진 자격이 인증됨을 의미
			Bearer는 "운반자" 또는 "소지자"라는 뜻입니다.
            즉, **"이 토큰을 가진 자(Bearer)는 인증된 사용자로 간주한다"**는 의미
           
            Authorization 헤더는 여러 인증 방식이 가능하기 때문에,
            Bearer, Basic, Digest 등 어떤 인증 방식을 사용하는지 구분이 필요합니다.
            
            
            클라이언트(Client)와 서버(Server)간의 통신을   상태유지(Stateful) 하느냐, 상태유지하지않음(Stateless) 으로 하느냐


			Stateless (무상태)
			무상태는 반대로 클라이언트와 서버 관계에서 [[ 서버가 클라이언트의 상태를 보존하지 않음 ]]을 의미한다.

			Stateless 구조에서 
			서버는 단순히 요청이 오면 응답을 보내는 역할만 수행하며, [[상태 관리는 전적으로 클라이언트에게 책임]]이 있는 것이다.
			즉, 클라이언트와 서버간의 통신에 필요한 [[모든 상태 정보들은 클라이언트에서 가지고 있다가]] 서버와 통신할때 
			데이터를 실어 보내는 것이 [[무상태 구조이다]]
            
		*/
		
		
		
		/*
		react 에서는 받는 토큰을 어떻게 할까요 ^^
		 
		// 로그인 요청 후 토큰 저장
		axios.post('/api/login', { username, password })
		  .then(res => {
		    const token = res.data.token;
		    localStorage.setItem('token', token);
		  });

		// 요청 시 토큰 헤더에 추가
		axios.get('/api/user', {
		  headers: {
		    Authorization: `Bearer ${localStorage.getItem('token')}`
		  }
		}); 
		  
		  
		 */
		return new ResponseEntity<String>(jwt,HttpStatus.OK);
	}
	
	/*
	headers: {
			    Authorization: `Bearer ${localStorage.getItem('token')}`
			  }
	클라이언트 전송


	*/
	@GetMapping("/user/info")
	public ResponseEntity<?> userInfo(@RequestHeader(name="Authorization") String header){
		
		//header => 'Bearer ${localStorage.getItem('token')}`
		log.info("header 정보");
		log.info("Authorization : "+ header);
		
		//서명
		String secretkey = jwtProps.getSecretkey();
				
		//서명이 사용될떼는 (토큰 byte[] 변환)
		byte[] signingkey = jwtProps.getSecretkey().getBytes(); //실제 토큰에 들어가는 값
				
		//Authorization > Bearer ..
		//public static final String TOKEN_PREFIX="Bearer ";
		String jwt = header.replace(SecurityConstants.TOKEN_PREFIX,"");
		
		//토큰 해석
		Jws<Claims> parsedToken = Jwts.parser()
									  .verifyWith(Keys.hmacShaKeyFor(signingkey))
									  .build()
									  .parseSignedClaims(jwt);
		log.info("parsedToken : " + parsedToken);
		String username= parsedToken.getPayload().get("uid").toString();
		log.info("username : " + username);
		
		Claims claims = parsedToken.getPayload(); //데이터 username, rols
		
		Object roles = claims.get("rol");
		log.info("roles :" + roles);
		
		
		return new ResponseEntity<String>(parsedToken.toString(), HttpStatus.OK);
	}
	
	/* 
	 header={typ=JWT, alg=HS512},payload={exp=1761876521, uid=hong, rol=[ROLE_USER, ROLE_ADMIN]},signature=ePx1FblwMt2-wWsCEcMe6DB2EiMeaki7RTKcZ2IG3jWxvYlKCPyiL7QTcVptSxeqyKU23zSFOBdy_iWxIK-RhA
	 */
	
}











