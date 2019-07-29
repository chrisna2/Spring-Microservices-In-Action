package com.tyn.bnk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.tyn.bnk.config.SvcConfig;

public class JwtStoreConfig {
	
	@Autowired
	SvcConfig svcConfig;
	
	@Bean
	public JwtAccessTokenConverter jwtAccessConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(svcConfig.getJwtSigningKey());
		
		return converter;
	}
	
	@Bean
	public TokenStore tokenStore() {
		
		return new JwtTokenStore(jwtAccessConverter());
	}
	
	@Bean
	@Primary//특정 타입의 빈이 둘이상일때 @Primary로 표시된 타입을 자동으로 주입하도록 스프링에 설정하는 역활을 한다.
	public DefaultTokenServices tokenServices() {
		DefaultTokenServices dts = new DefaultTokenServices();
		dts.setTokenStore(tokenStore());
		dts.setSupportRefreshToken(true);
		
		return dts;
	}
	
	/*
	@Bean
	public TokenEnhancer jwtTokenEnhancer() {
	
		return new JwtTokenEnhancer();
	}
	*/
}
