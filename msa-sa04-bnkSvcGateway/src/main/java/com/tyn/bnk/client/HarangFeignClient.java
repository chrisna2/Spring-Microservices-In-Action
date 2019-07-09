package com.tyn.bnk.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//대안2 - @FeignClient
//서비스서버에서 다른 다른 서비스서버를 호출하는 방법중 가장 좋은 방법
@FeignClient("harang")
public interface HarangFeignClient {
		
	@RequestMapping(value = "/test", 
					method = RequestMethod.GET,
					consumes = "application/json")
	Map<String, Object> test();
	
	@RequestMapping(value = "/dataCheck", 
			method = RequestMethod.GET,
			consumes = "application/json")
	List<Map<String, Object>> testData();

}
