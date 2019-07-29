package com.tyn.bnk.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.tyn.bnk.repository.AuthRepository;


//JwtTokenEnhancer클래스를 인증 서비스에 추가 하면 JWT토큰을 쉽게 확장이 가능하다.
public class JwtTokenEnhancer implements TokenEnhancer{
	
	@Autowired
	AuthRepository authRepository;
	
	
	private String getName(String m_id) {
		
		return authRepository.getNameByMid(m_id);
	}
	
	

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
	//보강을 위해 enhance() 재정의 해야 됨	
		Map<String, Object> additionInfo = new HashMap<String, Object>();
		
		String m_name = getName(authentication.getName());
		
		additionInfo.put("m_name",m_name);
		
		//반드시 모든 추가 속성은 HashMap애 추가하고 메서드에 전달된 accessToken변수에 설정된다.
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionInfo);
		
		return accessToken;
	}
	
	
	
}
