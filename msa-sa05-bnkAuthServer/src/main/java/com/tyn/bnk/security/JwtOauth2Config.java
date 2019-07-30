package com.tyn.bnk.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
public class JwtOauth2Config extends AuthorizationServerConfigurerAdapter{
	//기존 버전 OAuth2Configuration를 대체 하는 클래스 

	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private UserDetailsService userDtlSvc;
	
	@Autowired
	private TokenStore tknStore;
	
    @Autowired
    private DefaultTokenServices defaultTokenServices;
	
	@Autowired
	private JwtAccessTokenConverter jwtAcsTknCoverter;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tknEnhancerChain = new TokenEnhancerChain();
		
		//tknEnhancerChain.setTokenEnhancers(Arrays.asList(jwtTknEnhancer,jwtAcsTknCoverter)); 
		//이렇게 인터페이스를 통해 tokenEnhancer을 연결하게 되면 제대로 인식이 안되고 추가가 안되서 그냥 여기다 집어 넣으니 실행 됨
		tknEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(),jwtAcsTknCoverter));
		
		endpoints.tokenStore(tknStore)//JwtStoreConfig에 정의 한 tokenStore가 여기에 삽입 된다.
				 .accessTokenConverter(jwtAcsTknCoverter)//이것은 스프링 시큐리티 Oauth2 코드가 JWT를 사용하도록 연결한다.
				 .tokenEnhancer(tknEnhancerChain)//configure()호출할때 전달된 endspoints 매개변수에 TokenEnhancerChain을 연결한다.
				 .authenticationManager(authManager)
				 .userDetailsService(userDtlSvc);
	}	

    // 토큰에 추가적인 정보를 넣어서 저장한다.
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JwtTokenEnhancer();
    }
    
    /*
    	JWT : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzb210aGluZyI6ImFudGhpbmdwbGVhc2UiLCJ1c2VyX25hbWUiOiJhZG1pbjAxIiwic2NvcGUiOlsid2ViY2xpZW50Il0sIlRFU1RfVkFVTEVfS0VZIjoiVGVzdCBWYWx1ZS4gcGxlYXNlIG91dCIsImV4cCI6MTU2NDUxNTQwMSwibWVtYmVyX25hbWUiOiLrgpjtmITquLAiLCJhdXRob3JpdGllcyI6WyJST0xFX0FETUlOIl0sImp0aSI6IjZjOTFjNjgzLTIwMWEtNDk0Mi1iYTkyLThkYWJjZDMyZDI0MiIsImNsaWVudF9pZCI6ImVhZ2xlZXllIn0.6Gz5qaunOtN3RvKHo2qFIETROE_hcdiJtmCFXGo1Jfg
    
    	=>
    
   		결과 : 
   		{
			 "somthing": "anthingplease",
			 "user_name": "admin01",
			 "scope": [
			  "webclient"
			 ],
			 "TEST_VAULE_KEY": "Test Value. please out",
			 "exp": 1564515401,
			 "member_name": "나현기",
			 "authorities": [
			  "ROLE_ADMIN"
			 ],
			 "jti": "6c91c683-201a-4942-ba92-8dabcd32d242",
			 "client_id": "eagleeye"
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
			   //.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("thisissecret"))//비밀번호:thisissecret
			   .secret("{noop}thisissecret")//비밀번호:thisissecret
			   .authorizedGrantTypes("refresh_token","password","client_credential")//
			   .scopes("webclient","mobileclient");
	}
}
