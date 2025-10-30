package com.example.demo.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
//@ConfigurationProperties("kr.or.kosa")  application.properties 접근가능
//kr.or.kosa.secret-key=[nvM7^e`?Br`I]wmssq;3~Lh{N|H]DwW{J9~N?FjEFsp^RGYobFmqb=nXqKz^E$J

@Component
@Data
@ConfigurationProperties("kr.or.kosa")  //
public class JwtProps {
	private String secretkey;
	//롬복 getter 를 사용해서 key값을이용
}
