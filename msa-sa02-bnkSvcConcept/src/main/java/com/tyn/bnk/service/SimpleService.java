package com.tyn.bnk.service;

import java.util.List;
import java.util.Map;

public interface SimpleService {
	
	public List<Map<String, String>> justSelect();
	public Map<String, String> getEmpInfo(String emp_no);

}
