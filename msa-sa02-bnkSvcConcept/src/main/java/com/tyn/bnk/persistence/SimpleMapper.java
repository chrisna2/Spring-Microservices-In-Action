package com.tyn.bnk.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimpleMapper {
	
	public List<Map<String, String>> justSelect();

}
