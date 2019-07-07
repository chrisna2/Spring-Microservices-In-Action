package com.tyn.bnk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy//서비스를 주울 서버로 사용한다.
public class MsaSa04BnkSvcGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaSa04BnkSvcGatewayApplication.class, args);
	}

}
