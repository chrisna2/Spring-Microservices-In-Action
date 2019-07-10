package com.tyn.bnk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override//사용자와 패스워드, 역활을 정의한다.
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
		    .passwordEncoder(encoder)
		    .withUser("client01").password(encoder.encode("password1")).roles("USER")
		    .and()
		    .withUser("admin01").password(encoder.encode("password2")).roles("USER","ADMIN");
	}

	@Override
	@Bean//스프링시큐리티에서 반환될 사용자 정보를 저장하는데 사용된다.
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}
	
	@Override
	@Bean//스프링 시큐리티에서 인증을 처리하는데 사용된다.
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	
}
