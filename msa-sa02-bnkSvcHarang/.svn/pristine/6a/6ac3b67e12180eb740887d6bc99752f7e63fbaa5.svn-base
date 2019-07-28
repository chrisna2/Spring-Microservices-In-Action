package com.tyn.bnk.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConceptDiscoveryClient {

	@Autowired
	private DiscoveryClient discoveryClient;
	
	public Map getEmpInfo(String emp_no){
		
		RestTemplate restTemplate = new RestTemplate();
		
		List<ServiceInstance> instances = discoveryClient.getInstances("concept");
		
		if(instances.size()==0) {
			return null;
		}
		
		String serviceUrl = String.format("%s/concept/emp/%s", 
										  instances.get(0).getUri().toString(),
										  emp_no);
		
		ResponseEntity<Map> restExchange = restTemplate.exchange(serviceUrl,
																 HttpMethod.GET,
																 null,
																 Map.class,
																 emp_no);
		
		
		return restExchange.getBody();
	}
	
}
