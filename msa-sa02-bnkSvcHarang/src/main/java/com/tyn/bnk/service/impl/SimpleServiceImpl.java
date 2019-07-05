package com.tyn.bnk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.tyn.bnk.client.ConceptDiscoveryClient;
import com.tyn.bnk.client.ConceptFeignClient;
import com.tyn.bnk.client.ConceptRestClient;
import com.tyn.bnk.persistence.SimpleMapper;
import com.tyn.bnk.service.SimpleService;
import com.tyn.bnk.utils.UserContextHolder;

@Service
public class SimpleServiceImpl implements SimpleService {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);
	
	@Autowired
	SimpleMapper mapper;
	
	@Autowired
	ConceptDiscoveryClient discoveryClient;
	
	@Autowired
	ConceptFeignClient feignClient;
	
	@Autowired
	ConceptRestClient restClient;
	
	@Override
	public List<Map<String, Object>> justSelect() {
		// TODO Auto-generated method stub
		return mapper.justSelect();
	}

	@Override
	public List<Map<String, Object>> srchMember(String m_id) {
		// TODO Auto-generated method stub
		return mapper.srchMember(m_id);
	}

	@Override
	public Map<String, String> getEmpInfoClientType(String emp_no, String clientType) {
		// TODO Auto-generated method stub
		
		Map<String, String> result = new HashMap<String, String>();
		
		switch(clientType) {
			case "feign":
				logger.info("I am using the feign client");
				result = feignClient.getEmpInfo(emp_no);
				break;
			case "rest":
				logger.info("I am using the rest client");
				result = restClient.getEmpInfo(emp_no);
				break;
			case "discovery":
				logger.info("I am using the discovery client");
				result = discoveryClient.getEmpInfo(emp_no);
				break;
			default:
				logger.info("I am using the rest client");
				result = restClient.getEmpInfo(emp_no);
		}
		
		return result;
	}

	@Override
	@HystrixCommand//@HystrixCommand는 회로 차단기로 getEmpInfo()메서드를 연결하는데 사용된다.
	public Map<String, String> getEmpInfo(String emp_no) {
		// TODO Auto-generated method stub
		Map<String, String> result = new HashMap<String, String>();
		result = feignClient.getEmpInfo(emp_no);
		return result;
	}
	
	public void ramdomlyRunning() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3-1)+1)+1;
		
		if(randomNum==3) {
			sleep();
		}
		
	}
	
	public void sleep() {
		try {
			//11초 동안 대기함 : 히스트릭스는 기번 적으로 1초 후에 호출을 타입아웃 한다.
			Thread.sleep(11000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@HystrixCommand
	public List<Map<String, Object>> srchMemberByGrade(int m_grade){
		logger.debug("Harang.srchMemberByGrade Correlation Id:{}",UserContextHolder.getContext().getCorrelationId());
		ramdomlyRunning();
		return mapper.srchMemberByDept(m_grade);
		
	}

}
