package com.tyn.bnk.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//대안2 - @FeignClient
//서비스서버에서 다른 다른 서비스서버를 호출하는 방법중 가장 좋은 방법
@FeignClient("concept")
public interface ConceptFeignClient {
		
	@RequestMapping(value = "/emp/{emp_no}", 
					method = RequestMethod.GET,
					consumes = "application/json")
	Map<String, String> getEmpInfo(@PathVariable("emp_no")String emp_no);

}
