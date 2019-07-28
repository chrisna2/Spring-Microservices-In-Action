package com.tyn.bnk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tyn.bnk.service.SimpleService;
import com.tyn.bnk.utils.UserContextHolder;

@RestController
public class SimpleClientController {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleClientController.class);
	
	@Autowired
	SimpleService service;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ResponseEntity<Map<String,String>> test(){
		
		ResponseEntity<Map<String,String>> response = null;
		Map<String,String> resMap = new HashMap<String, String>();
		
		resMap.put("type","Second Eureka Client!");
		resMap.put("msg","Spring cloud is Hard :-<");
		
		response =new ResponseEntity<Map<String,String>>(resMap, HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(value = "/dataCheck", method = RequestMethod.GET)
	ResponseEntity<List<Map<String,Object>>> testData(){
		
		ResponseEntity<List<Map<String,Object>>> response = null;
		List<Map<String,Object>> resMap = new ArrayList<Map<String,Object>>();
		
		resMap = service.justSelect();
		
		response =new ResponseEntity<List<Map<String,Object>>>(resMap, HttpStatus.OK);
		
		return response;
	}
	
	
	@RequestMapping(value = "/srchAllTblLesson", method = RequestMethod.GET)
	ResponseEntity<List<Map<String,Object>>> srchAllTblLesson(){
		
		ResponseEntity<List<Map<String,Object>>> response = null;
		List<Map<String,Object>> resMap = new ArrayList<Map<String,Object>>();
		resMap = service.srchAllTblLesson();
		response =new ResponseEntity<List<Map<String,Object>>>(resMap, HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(value = "/srchAllTblParttime", method = RequestMethod.GET)
	ResponseEntity<List<Map<String,Object>>> srchAllTblParttime(){
		
		ResponseEntity<List<Map<String,Object>>> response = null;
		List<Map<String,Object>> resMap = new ArrayList<Map<String,Object>>();
		resMap = service.srchAllTblParttime();
		response =new ResponseEntity<List<Map<String,Object>>>(resMap, HttpStatus.OK);
		
		return response;
	}
	
	
	@RequestMapping(value="/empbynotype/{emp_no}/{clientType}" , method = RequestMethod.GET)
	ResponseEntity<Map<String,String>> getEmpInfoClientType(@PathVariable("emp_no") String emp_no,
														    @PathVariable("clientType") String clientType){
		
		ResponseEntity<Map<String,String>> response = null;
		
		response = new ResponseEntity<Map<String,String>>(service.getEmpInfoClientType(emp_no, clientType),
														  HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(value="/empbyno/{emp_no}" , method = RequestMethod.GET)
	ResponseEntity<Map<String,String>> getEmpInfoClientType(@PathVariable("emp_no") String emp_no){
		
		ResponseEntity<Map<String,String>> response = null;
		
		response = new ResponseEntity<Map<String,String>>(service.getEmpInfo(emp_no),HttpStatus.OK);
		
		return response;
	}
	
	@RequestMapping(value="/memberbygrade/{m_grade}" , method = RequestMethod.GET)
	ResponseEntity<List<Map<String,Object>>> srchMemberByGrade(@PathVariable("m_grade")int m_grade){
		
		logger.info("Harang.SimpleClientController Correlation id: {}", UserContextHolder.getContext().getCorrelationId());
		
		ResponseEntity<List<Map<String,Object>>> response = null;
		
		response = new ResponseEntity<List<Map<String,Object>>>(service.srchMemberByGrade(m_grade),HttpStatus.OK);
		
		return response;
	}


}
