package com.tyn.bnk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SvcConfig {

	@Value("${signing.key}")
	private String jwtSigningKey="";

	public String getJwtSigningKey() {
		return jwtSigningKey;
	}
	
}
