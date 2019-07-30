package com.tyn.bnk.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.tyn.bnk.config.SvcConfig;

@Configuration
public class JwtStoreConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtStoreConfig.class);
	
	@Autowired
	SvcConfig svcConfig;
	
	@Bean
	public TokenStore tokenStore() {
		
		return new JwtTokenStore(jwtAccessConverter());
	}
	
	@Bean
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices dts = new DefaultTokenServices();
		dts.setTokenStore(tokenStore());
		dts.setSupportRefreshToken(true);
		
		return dts;
	}
	
	@Bean
	@Primary//특정 타입의 빈이 둘이상일때 @Primary로 표시된 타입을 자동으로 주입하도록 스프링에 설정하는 역활을 한다.
	public JwtAccessTokenConverter jwtAccessConverter() {
		//JWT와 Oauth2서버 사이의 변환기로 작동한다.
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		//토큰 서명에 사용되는 서명 키를 정의 한다.
		converter.setSigningKey(svcConfig.getJwtSigningKey());
		return converter;
	}
	
//	@Bean
//	public TokenEnhancer jwtTokenEnhancer() {
//		return new JwtTokenEnhancer();
//	}
}
