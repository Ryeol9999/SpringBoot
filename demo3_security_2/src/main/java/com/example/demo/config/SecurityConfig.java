package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

//Spring boot 에서 설정 (xml) 하지 않고 java 파일(@Configuration 빈객체 생성 주입)

@Configuration
@EnableWebSecurity  //이 클래스 스프링 시큐리티 설정 가능  (버전:5.x.x > 6.x.x 변화가 큼)
@RequiredArgsConstructor
public class SecurityConfig {
	
	//@Bean
	//public Emp call{ return new Emp()}
	
	private final DataSource dataSource;
	//@RequiredArgsConstructor  lombok
	//implementation 'org.springframework.boot:spring-boot-starter-jdbc'
	
	
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		// 인증과 권환( 한곳에 모아서 처리하기를 원해요)
		// 자원에 대해서 (특정 페이지, 특성 폴더, 특정 자원)
		// Url > /admidn >> ROLL_USER 안되고 ROLL_ADMIN 권한만 가능
		
		/*
		 spring regacy 사용한 security 3.x.x
		   <security:intercept-url pattern="/customer/noticeDetail.do" access="hasRole('ROLE_USER')" />
          <security:intercept-url pattern="/customer/noticeReg.do" access="hasRole('ROLE_ADMIN')" />

        spring boot   security 5.x.x  
		   http.authorizeRequests()
				.antMatchers("/admin","/admin/**").hasRole("ADMIN")
				.antMatchers("/user","/user/**").hasAnyRole("USER","ADMIN")
				.antMatchers("/css/**" , "/js/**" , "/imges/**").permitAll()
				.antMatchers("/**").permitAll()
				.anyRequest().authenticated();
		spring boot security 6.x.x 람다 표현식
		보통 spring boot 로 : spring framework 6.x + spring boot 3.x +security 6.x
		 */
		
		http.authorizeHttpRequests(auth ->  auth.requestMatchers("/admin/**").hasRole("ADMIN")
								                .requestMatchers("/user/**").hasAnyRole("USER","ADMIN") //ROLE_USER , ROLE_ADMIN
								                .requestMatchers("/css/**" , "/js/**" , "/images/**")
								                .permitAll()
								                .anyRequest().permitAll())
												.formLogin(withDefaults()); //로그인 방식 기본값 유지

		http.logout(logout -> logout.logoutUrl("/logout")       	//로그아웃 요청을 받을 URL
									.logoutSuccessUrl("/")			//로그아웃 성공후에 이동할 URL
									.deleteCookies("JSESSIONID")	//쿠키 삭제 
									.invalidateHttpSession(true)	//세션 객체 삭제
									);
		return http.build();
	}
	
	/*
	 http
	.formLogin(form -> form
   .loginPage("/login")              // 내가 만든 로그인 페이지 URL
   .loginProcessingUrl("/loginProc") // 로그인 요청을 처리할 URL (Controller 필요 없음)
   .usernameParameter("userId")      // 로그인 폼에서 name="userId" 인 입력 필드 사용
   .passwordParameter("userPw")      // name="userPw"
   .defaultSuccessUrl("/home", true) // 로그인 성공 시 이동
   .failureUrl("/login?error=true")  // 실패 시 이동
   .permitAll()
   loginProcessingUrl()은 Spring Security가 직접 처리하기 때문에
   Controller에서 /loginProc 매핑을 만들 필요가 없습니다.
    );
 
	 */
	
	
	
	
	
	/*===================================================*/
	
	
	/*
	 예전 방식은 사용자(in-memory) 방식은 사용자, 암호, 권한 설정해서 TEST
	<security:user-service>
		<security:user name="hong"  password="1004" authorities="ROLE_USER"/>
		<security:user name="admin" password="1004" authorities="ROLE_USER,ROLE_ADMIN"/>
	</security:user-service>
	 */
	//인증 권한 (in-memory)
	
	@Bean
	public UserDetailsService userDetailsService() {  //사용자 정보 (원칙 DB 가지고.... in memory TEST
		/*
		UserDetails user = User.builder()
							   .username("user")
							   .password(passwordEncoder().encode("1004"))
							   .roles("USER")		   
							   .build();
		
		UserDetails admin = User.builder()
							    .username("admin")
							    .password(passwordEncoder().encode("1007"))
							    .roles("USER","ADMIN")		   
							    .build();
						 
		
		return new InMemoryUserDetailsManager(user, admin);  //in memory 방식 사용자 생성
	*/
		//JDBC 연결
	    JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // 사용자 정보 쿼리
        String sql1 = "SELECT user_id AS username, user_pw AS password, enabled FROM user WHERE user_id = ?";
        // 권한 정보 쿼리
        String sql2 = "SELECT user_id AS username, auth FROM user_auth WHERE user_id = ?";

        userDetailsManager.setUsersByUsernameQuery(sql1);
        userDetailsManager.setAuthoritiesByUsernameQuery(sql2);

        return userDetailsManager;
		}
	
	//authenticationManager 구현하기
		@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration ) throws Exception
		{
			return authenticationConfiguration.getAuthenticationManager();		
		}
		
		
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
