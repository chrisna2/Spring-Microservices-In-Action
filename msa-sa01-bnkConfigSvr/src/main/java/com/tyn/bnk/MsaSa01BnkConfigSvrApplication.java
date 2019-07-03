package com.tyn.bnk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//@EnableEurekaClient
@EnableConfigServer
public class MsaSa01BnkConfigSvrApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsaSa01BnkConfigSvrApplication.class, args);
	}

}
