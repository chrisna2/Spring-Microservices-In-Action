package com.tyn.bnk.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.tyn.bnk.client.ConceptDiscoveryClient;
import com.tyn.bnk.client.ConceptFeignClient;
import com.tyn.bnk.client.ConceptRestClient;
import com.tyn.bnk.persistence.SimpleMapper;
import com.tyn.bnk.service.SimpleService;
import com.tyn.bnk.utils.UserContextHolder;

@Service
//히스트릭스의 설정의 기본 값을 구성, @HystrixCommand를 설정한 모든 메소드에 해당 설정이 적용된다.
@DefaultProperties(
			threadPoolKey = "defaultTreadPool",
			threadPoolProperties = {
					@HystrixProperty(name = "coreSize", value="30"),
					//스레드풀 앞에 배치할 큐와 큐에 넣을 요청 수를 정의
					@HystrixProperty(name = "maxQueueSize", value = "10")
			}
			/*
			 //현재 "execution.isolation.thread.timeoutInMiliseconds" 클래스가 존재 하지 않는다는 에러가 발생해서 일단 주석으로 처리
			,commandProperties = {
					//회로차단기의 타임아웃 시간(밀리초)을 설정하는 데 사용되는 timeoutInMilisecond 프로퍼티, 호출이 실패하기 전까지 대기할 최대 타임아웃 시간을 10초로 설정
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMiliseconds", value = "10000")
			}*/
		)
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
	//일부러 에러를 발생시키려고 만든 임시 메소드
	public void ramdomlyRunning() {
		Random rand = new Random();
		int randomNum = rand.nextInt((3-1)+1)+1;
		
		if(randomNum==3) {
			sleep();
		}
		
	}
	public void sleep() {
		try {
			//11초 동안 대기함 : 히스트릭스는 기본적으로 1초 후에 호출을 타입아웃 한다.
			Thread.sleep(11000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	//히스트릭스 개별 설정
	@HystrixCommand(
				//히스트릭스에서 호출이 실패할 때 불러오는 클래스 함수를 정의
				fallbackMethod = "fallbackSrchMemberByGrade",
				//벌크 헤드 패턴 구현 : 스레드풀을 공유 하지 않고 개별 서비스에 스레드풀을 지정하여 한 서비스가 모든 스레드풀의 자원을 독점하지 않게 하는 것
				//스레드풀의 고유 이름을 정의 한다.
				threadPoolKey = "memberByGradeTreadPool",
				threadPoolProperties = {
						//스레드풀의 스레드 갯수를 정의
						@HystrixProperty(name = "coreSize", value="30"),
						//스레드풀 앞에 배치할 큐와 큐에 넣을 요청 수를 정의
						@HystrixProperty(name = "maxQueueSize", value = "10")
				},
				//히스트릭스를 사용자 정의 하기 위해 추가 매개변수를 전달하는 commandProperties, 객체 배열 허용(여러개 입력가능)
				commandProperties = {
						//회로차단기의 타임아웃 시간(밀리초)을 설정하는 데 사용되는 timeoutInMilisecond 프로퍼티, 호출이 실패하기 전까지 대기할 최대 타임아웃 시간을 12초로 설정
						//@HystrixProperty(name = "execution.isolation.thread.timeoutInMiliseconds", value = "12000"),
						//히스트릭스가 호출차단을 고려하는데 필요한 10초 시간대 동안 연속 호출 횟수를 제어 : 10번 까지
						@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
						//회로 차단기를 차단하고 위에 값만큼 호출한 후 실패가 발생해야 하는 비율 : 현재 75%
						@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
						//차단되고 나서 히스트릭스가 서비스의 회복상태를 확인할 때까지 대기하는 시간 : 현재 7초 
						@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "7000"),
						//히스트릭스가 서비스 호출문제를 모니터할 시간 간격을 설정하는데 사용 : 현재 15초
						@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "15000"),
						//설정한 시간 간격 동안 통계를 수집할 횟수를 설정 : 현재 5회
						@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5")
				}
			)
	public List<Map<String, Object>> srchMemberByGrade(int m_grade){
		logger.info("Harang.SimpleServiceImpl Correlation Id:{}",UserContextHolder.getContext().getCorrelationId());
		ramdomlyRunning();
		return mapper.srchMemberByGrade(m_grade);
	}
	//위에서 정의한 fallback함수
	public List<Map<String, Object>> fallbackSrchMemberByGrade(int m_grade){
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		
		Map<String, Object> failMsg = new HashMap<String, Object>();
		
		failMsg.put("m_grade",m_grade);
		failMsg.put("failMsg","Sorry we can't find Member Information currently available");
		failMsg.put("m_grade",m_grade);
		
		resultList.add(failMsg);
		
		return resultList;
	}

	@Override
	public List<Map<String, Object>> srchAllTblLesson() {
		return mapper.srchAllTblLesson();
	}

	@Override
	public List<Map<String, Object>> srchAllTblParttime() {
		return mapper.srchAllTblParttime();
	}

}
