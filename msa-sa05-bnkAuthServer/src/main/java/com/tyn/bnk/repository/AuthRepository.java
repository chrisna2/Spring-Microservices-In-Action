package com.tyn.bnk.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthRepository {

	public String getNameByMid(String m_id)	;

}