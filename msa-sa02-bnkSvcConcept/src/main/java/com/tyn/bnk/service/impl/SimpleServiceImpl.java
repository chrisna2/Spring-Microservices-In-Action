package com.tyn.bnk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyn.bnk.persistence.SimpleMapper;
import com.tyn.bnk.service.SimpleService;

@Service
public class SimpleServiceImpl implements SimpleService {

	@Autowired
	SimpleMapper mapper;
	
	@Override
	public List<Map<String, String>> justSelect() {
		// TODO Auto-generated method stub
		return mapper.justSelect();
	}

}
