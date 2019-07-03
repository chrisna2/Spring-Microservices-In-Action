package com.tyn.bnk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MsaSa02BnkSvcConceptApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaSa02BnkSvcConceptApplication.class, args);
	}

}
