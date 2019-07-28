package com.tyn.bnk.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration//반드시 이 어노테이션 설정 추가 해야됨
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	@Override//모든 접근 규칙을 재정의한 configure()메서드 안에 정의 해야 됨
	public void configure(HttpSecurity http) throws Exception {
		//메서드로 전달된 HttpSecurity 객체로 모든 접근 규칙이 구성된다.
		http.authorizeRequests()
			//컨트롤러에 정의된 url에 대하여 각각의 권한별 접근 제한을 걸여 준다.
			.antMatchers(HttpMethod.GET, "/test")
			.hasAnyRole("ADMIN","MEMBER","NEWBEE")
		    .antMatchers(HttpMethod.GET, "/srchAllTblLesson", "/srchAllTblParttime", "/dataCheck")
		    .hasAnyRole("ADMIN","MEMBER")
			.antMatchers(HttpMethod.GET, "/memberbygrade/**", "/empbynotype/**", "/empbyno/**")
			.hasRole("ADMIN")//접근할 수 있는 역할을 쉽표로 구분한 목록이다.
			//인가 규칙을 정의하는 마지막 부분에서 서비스의 모든 엔드포인트도 인증된 사용자로 접근 되어야 한다고 정의함
			.anyRequest()
			.authenticated();
		
		//모든 접근 규칙을 재정의한 configure() 메서드 안에서 스프링이 전달한 HttpSecurity클래스를 사용해 정의 된다.
		//이 예제에서는 조직서비스의 모든 URL에 대해 인증된 사용자만 접근하도록 제한한다.

	}
	
	
	
	

}
