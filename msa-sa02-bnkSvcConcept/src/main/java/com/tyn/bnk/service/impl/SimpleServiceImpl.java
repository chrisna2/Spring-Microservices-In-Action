package com.tyn.bnk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyn.bnk.event.source.SimpleSourceBean;
import com.tyn.bnk.persistence.SimpleMapper;
import com.tyn.bnk.service.SimpleService;

@Service
public class SimpleServiceImpl implements SimpleService {
	
	private final static Logger logger = LoggerFactory.getLogger(SimpleServiceImpl.class);

	@Autowired
	SimpleMapper mapper;
	
	@Autowired
	SimpleSourceBean simpleSourceBeam;
	
	@Override
	public List<Map<String, String>> justSelect() {
		return mapper.justSelect();
	}
	@Override
	public Map<String, String> getEmpInfo(String emp_no) {
		return mapper.getEmpInfo(emp_no);
	}
	@Override
	public Map<String, String> saveEmpInfo(Map<String, String> map) {
		
		mapper.saveEmpInfo(map);
		logger.info("@saveEmpInfo => "+map.toString());
		
		simpleSourceBeam.publishConceptChange("SAVE", map.get("EMP_NO"));
		//조직 데이터를 변경 하는 메서드 모두 simpleSourceBeam.publishConceptChange()을 호출한다.
		
		return map;
	}
	@Override
	public Map<String, String> updEmpInfo(Map<String, String> map) {
		
		mapper.updEmpInfo(map);
		logger.info("@updEmpInfo => "+map.toString());
		
		simpleSourceBeam.publishConceptChange("UPDATE", map.get("EMP_NO"));
		//조직 데이터를 변경 하는 메서드 모두 simpleSourceBeam.publishConceptChange()을 호출한다.
		
		return map;
	}

}
