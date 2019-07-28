package com.tyn.bnk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

//Http헤더의 정보의 전파를 보장하는 인터셉트 클래스 : ClientHttpRequestInterceptor 
public class UserContextInterceptor implements ClientHttpRequestInterceptor {
	
    private static final Logger logger = LoggerFactory.getLogger(UserContextInterceptor.class);
    @Override
    public ClientHttpResponse intercept(
    		//RestTemplate으로 실제 HTTP 서비스 호출을 하기 전에 이 intercept()가 호출된다.
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        //서비스 호출을을 위해 준비할 Http요청 해더를 가져와 UserContex에 저장된 Correlation_Id를 추가한다. 다른 어떤 정보도 가져올수 있다.
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());

        return execution.execute(request, body);
    }
    //이 인터셉트를 제대로 사용하려면 Applicatin 클래스에  RestTemplate빈을 정의하고 이 해당 인터셉트를 그 빈에 추가해야 된다.
}