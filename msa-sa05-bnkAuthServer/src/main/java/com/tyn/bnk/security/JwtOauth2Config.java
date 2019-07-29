package com.tyn.bnk.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

public class JwtOauth2Config extends AuthorizationServerConfigurerAdapter{
	//기존 버전 OAuth2Configuration를 대체 하는 클래스 
	

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserDetailsService userDtlSvc;
	
	@Autowired
	private TokenStore tknStore;
	
	@Autowired
	private DefaultTokenServices defaultTknSvcs;
	
	@Autowired
	private JwtAccessTokenConverter jwtAcsTknCoverter;
	
	@Autowired
	private TokenEnhancer jwtTknEnhancer;
	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tknEnhancerChain = new TokenEnhancerChain();
		
		tknEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTknEnhancer,jwtAcsTknCoverter));
		
		endpoints.tokenStore(tknStore)//JwtStoreConfig에 정의 한 tokenStore가 여기에 삽입 된다.
				 .accessTokenConverter(jwtAcsTknCoverter)//이것은 스프링 시큐리티 Oauth2 코드가 JWT를 사용하도록 연결한다.
				 .authenticationManager(authManager)
				 .userDetailsService(userDtlSvc);
	}	
	/* 
		//기존 버전
		@Override // 이 메서드는 AuthorizationServerConfigurerAdapter 안에서 사용될 여러 컴포넌트를 정의한다.
		//이 코드는 스프링에 기본 인증 관리자와 스프링과 함께 제공되는 사용자 상세 서비스를 이용한다고 알려준다.
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.authenticationManager(authenticationManager)
					 .userDetailsService(userDetailsService);
		}
	 */
		
	@Override // 서비스에 등록될 클라이언트를 정의 하는 configure() 메서드를 정의한다.
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
	//이건 기존 것과 동일함 	
		
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
	
	
}
