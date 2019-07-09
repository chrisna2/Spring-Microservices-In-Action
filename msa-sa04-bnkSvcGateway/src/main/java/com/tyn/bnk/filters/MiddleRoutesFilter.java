package com.tyn.bnk.filters;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.tyn.bnk.client.ConceptFeignClient;
import com.tyn.bnk.client.HarangFeignClient;

@Component
public class MiddleRoutesFilter extends ZuulFilter {
    private static final int FILTER_ORDER =  1;
    private static final boolean SHOULD_FILTER =true;
    
    private static final Logger logger = LoggerFactory.getLogger(MiddleRoutesFilter.class);
    
    @Autowired
    FilterUtils filterUtils;

	@Autowired
	ConceptFeignClient conceptFeignClient;
	
	@Autowired
	HarangFeignClient harangFeignClient;
    
    @Override
    public String filterType() {
        return FilterUtils.ROUTE_FILTER_TYPE;//"route" : 경로 필터 클래스임을 나타내는 키워드
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
    	
        RequestContext ctx = RequestContext.getCurrentContext();
        Map<String, String> empinfo = conceptFeignClient.getEmpInfo(filterUtils.getEmpNo());
        
        /*
         *로그인 사용자 권한에 따라 화면 리다이렉트 기능 구현 예정 
         */
        
        return null;
    }

}
