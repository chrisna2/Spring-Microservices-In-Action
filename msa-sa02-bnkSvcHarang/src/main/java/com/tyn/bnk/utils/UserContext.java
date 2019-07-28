package com.tyn.bnk.utils;

import org.springframework.stereotype.Component;

@Component
public class UserContext {
//Http요청에서 추출한 값을 보관하는  POJO 클래스
	
    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "Authorization";
    public static final String USER_ID        = "tmx-user-id";
    public static final String EMP_NO         = "tmx-emp-no";

    private static final ThreadLocal<String> correlationId= new ThreadLocal<String>();
    private static final ThreadLocal<String> authToken= new ThreadLocal<String>();
    private static final ThreadLocal<String> userId = new ThreadLocal<String>();
    private static final ThreadLocal<String> empNo = new ThreadLocal<String>();

    public static String getCorrelationId() { 
    	return correlationId.get();
    }
    
    public static void setCorrelationId(String cId) {
        correlationId.set(cId);
    }

    public static String getAuthToken() {
        return authToken.get();
    }

    public static void setAuthToken(String aToken) {
    	authToken.set(aToken);
    }

    public static String getUserId() {
        return userId.get();
    }

    public static void setUserId(String uId) {
        userId.set(uId);
    }

    public static String getEmpNo() {
        return empNo.get();
    }

    public static void setEmpNo(String eNo) {
        empNo.set(eNo);
    }

}