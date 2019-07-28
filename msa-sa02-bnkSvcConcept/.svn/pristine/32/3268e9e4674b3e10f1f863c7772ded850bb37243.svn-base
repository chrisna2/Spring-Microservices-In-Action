package com.tyn.bnk.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tyn.bnk.service.SimpleService;

@RestController
public class SimpleClientController {
	
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
	ResponseEntity<List<Map<String,String>>> testData(){
		
		ResponseEntity<List<Map<String,String>>> response = null;
		List<Map<String,String>> resMap = new ArrayList<Map<String,String>>();
		
		resMap = service.justSelect();
		
		response =new ResponseEntity<List<Map<String,String>>>(resMap, HttpStatus.OK);
		
		return response;
	}
	
	
	@RequestMapping(value = "/emp/{emp_no}", method = RequestMethod.GET)
	ResponseEntity<Map<String,String>> getEmpInfo(@PathVariable String emp_no){
		
		ResponseEntity<Map<String,String>> response = null;
		
		Map<String,String> resMap = new HashMap<String, String>();
		
		resMap = service.getEmpInfo(emp_no);
		
		response =new ResponseEntity<Map<String,String>>(resMap, HttpStatus.OK);
		
		return response;
	}
	
}
