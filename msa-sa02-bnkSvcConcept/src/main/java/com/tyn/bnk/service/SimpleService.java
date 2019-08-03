package com.tyn.bnk.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SimpleService {
	
	public List<Map<String, String>> justSelect();
	public Map<String, String> getEmpInfo(String emp_no);
	public Map<String, String> saveEmpInfo(Map<String, String> map);
	public Map<String, String> updEmpInfo(Map<String, String> map);

}
