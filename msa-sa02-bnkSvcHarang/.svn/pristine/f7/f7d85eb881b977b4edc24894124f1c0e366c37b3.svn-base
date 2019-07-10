package com.tyn.bnk.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
//Http요청에서 추출한 값을 보관하는  POJO 클래스
	
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String EMP_NO         = "tmx-emp-no";

    private String correlationId= new String();
    private String authToken= new String();
    private String userId = new String();
    private String empNo = new String();

    public String getCorrelationId() { 
    	return correlationId;
    }
    
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

}