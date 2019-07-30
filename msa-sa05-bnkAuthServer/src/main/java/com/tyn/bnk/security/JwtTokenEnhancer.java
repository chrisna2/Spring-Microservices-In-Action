package com.tyn.bnk.security;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.tyn.bnk.repository.AuthRepository;


//JwtTokenEnhancer클래스를 인증 서비스에 추가 하면 JWT토큰을 쉽게 확장이 가능하다.
//여기서는 받아온 유저의 아이디로 해당 유저의 이름을 추가하는 작업을 함
public class JwtTokenEnhancer implements TokenEnhancer{
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenEnhancer.class);
	
	@Autowired
	AuthRepository authRepository;
	
	private String getName(String m_id) {
		return authRepository.getNameByMid(m_id);
	}
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		//보강을 위해 enhance() 재정의 해야 됨	
		
		final Map<String, Object> additionInfo = new HashMap<String, Object>();
		
		logger.info("@JwtTokenEnhancer_authentication.getName() -> "+ authentication.getName());
		
		String m_name = getName(authentication.getName());
		
		logger.info("@JwtTokenEnhancer_m_name -> "+ m_name);
		
		additionInfo.put("member_name",m_name);
		additionInfo.put("TEST_VAULE_KEY","Test Value. please out");
		additionInfo.put("somthing","anthingplease");
		
		//반드시 모든 추가 속성은 HashMap애 추가하고 메서드에 전달된 accessToken변수에 설정된다.
		((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionInfo);
		
		return accessToken;
	}
	
	
	
}
