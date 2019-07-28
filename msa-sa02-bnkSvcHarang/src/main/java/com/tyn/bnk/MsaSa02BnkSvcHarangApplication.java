package com.tyn.bnk;

import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

import com.tyn.bnk.utils.UserContextInterceptor;

//2장+기본 스프링 부트 실행시 applicaton runner
@SpringBootApplication
//4장+유레카 서버, Feign, Discovery client
@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
//5장+서킷브레이커 실행
@EnableCircuitBreaker
//7장+해당 마이크로 서비스를 보호자원으로 지정
@EnableResourceServer
public class MsaSa02BnkSvcHarangApplication {
	
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
		SpringApplication.run(MsaSa02BnkSvcHarangApplication.class, args);
	}

}
