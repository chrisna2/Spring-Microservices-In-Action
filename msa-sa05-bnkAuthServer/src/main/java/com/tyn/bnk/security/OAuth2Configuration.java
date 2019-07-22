package com.tyn.bnk.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration //AuthorizationServerConfigurerAdapter 클래스 확장후 @Configuration 장착한다.
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource datasource;
	
	@Override // 서비스에 등록될 클라이언트를 정의 하는 configure() 메서드를 정의한다.
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		/*
		//우리는 DB에 연결해야 되니 데이터 소스 호출
		clients.jdbc(datasource)
			   .withClient("eagleeye")//어플리케이션 이름
			   //.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("thisissecret"))//비밀번호:thisissecret
			   .secret("{noop}thisissecret")//비밀번호:thisissecret
			   .authorizedGrantTypes("refresh_token","password","client_credential")//그랜트 타입 설정
			   .scopes("webclient","mobileclient");
		 */
		//현재 책에서는 인메모리(in-memory) 저장소를 이용
		clients.inMemory()
			   .withClient("eagleeye")//어플리케이션 이름
			   //.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("{noop}thisissecret"))//비밀번호:thisissecret
			   .secret("{noop}thisissecret")//비밀번호:thisissecret
			   .authorizedGrantTypes("refresh_token","password","client_credential")//
			   .scopes("webclient","mobileclient");
	}
	
	@Override // 이 메서드는 AuthorizationServerConfigurerAdapter 안에서 사용될 여러 컴포넌트를 정의한다.
	//이 코드는 스프링에 기본 인증 관리자와 스프링과 함께 제공되는 사용자 상세 서비스를 이용한다고 알려준다.
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				 .userDetailsService(userDetailsService);
	}
	
	

}
