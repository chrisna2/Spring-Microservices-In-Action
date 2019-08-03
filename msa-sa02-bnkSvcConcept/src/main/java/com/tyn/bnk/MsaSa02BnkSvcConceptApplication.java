package com.tyn.bnk;

import java.util.Collections;
import java.util.List;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import com.tyn.bnk.utils.UserContextInterceptor;

@SpringBootApplication
@EnableEurekaClient
//5장+
@EnableCircuitBreaker
//7장+
@EnableResourceServer
//8장+
@EnableBinding(Source.class)//스프링 클라우드 스트림에 애플리케이션을 메시지 브로커로 바인딩하라고 알린다.
public class MsaSa02BnkSvcConceptApplication {
	
	@Bean
	@LoadBalanced//리본 사용
	public RestTemplate getRestTemplate() {
	//UserContextInterceptor 활성화	
        RestTemplate template = new RestTemplate();
        List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
        if (interceptors==null){
        	//UserContextInterceptor를 생성된 RestTemplate인스턴스에 추가한다.
            template.setInterceptors(Collections.singletonList(new UserContextInterceptor()));
        }
        else{
            interceptors.add(new UserContextInterceptor());
            template.setInterceptors(interceptors);
        }
        return template;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MsaSa02BnkSvcConceptApplication.class, args);
	}
}
