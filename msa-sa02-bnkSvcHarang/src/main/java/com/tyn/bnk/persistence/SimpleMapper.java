package com.tyn.bnk.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SimpleMapper {
	
	public List<Map<String, Object>> justSelect();
	
	public List<Map<String, Object>> srchAllTblLesson();
	public List<Map<String, Object>> srchAllTblParttime();
	
	//public List<Map<String, Object>> srchAllTblPlayground();
	//public List<Map<String, Object>> srchAllTblStudyroom();
	
	public List<Map<String, Object>> srchMember(String m_id);
	
	public List<Map<String, Object>> srchMemberByGrade(int m_grade);

}
