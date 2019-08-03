package com.tyn.bnk.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimpleMapper {
	
	public List<Map<String, String>> justSelect();
	public Map<String, String> getEmpInfo(String emp_no);
	public int saveEmpInfo(Map<String, String> map);
	public int updEmpInfo(Map<String, String> map);

}
