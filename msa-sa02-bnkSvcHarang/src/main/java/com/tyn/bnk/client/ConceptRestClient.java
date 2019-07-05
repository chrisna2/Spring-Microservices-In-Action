package com.tyn.bnk.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


//대안 1 - restTemplate
@Component
public class ConceptRestClient {

	@Autowired
	RestTemplate restTemplate;
	
	public Map getEmpInfo(String emp_no){
		ResponseEntity<Map> restExchange = restTemplate.exchange(
				"http://concept/emp/{emp_no}",
				HttpMethod.GET, 
				null,
				Map.class,
				emp_no);
		return restExchange.getBody();
	} 
	
}
