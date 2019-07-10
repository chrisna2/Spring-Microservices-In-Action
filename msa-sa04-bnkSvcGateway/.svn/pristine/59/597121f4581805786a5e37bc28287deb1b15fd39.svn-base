package com.tyn.bnk.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PreChkFilter extends ZuulFilter{
//모든 줄 필터는 ZuulFilter클래스를 확장하고 오버라이드해야 하는 아래 4개의 메소드를 재정의해야 한다.
	
	private static final Logger logger = LoggerFactory.getLogger(PreChkFilter.class);
	
	private static final int FILTER_ORDER = 1; // 요청을 보내야 순서
	private static final boolean SHOULD_FILTER = true;
	
	@Autowired
	FilterUtils filterUtils;
	//모든 필터에서 공통으로 사용되는 기능을 FilterUtils 클래스에 담는다.
	
	@Override
	public String filterType() {
		// 주울에서 사전(pre)-경로(route)-사후(post) 필터를 지정하는데 사용된다.
		return FilterUtils.PRE_FILTER_TYPE;//"pre" : 사전 필터임을 나타내는 키워드
	}
	
	@Override
	public int filterOrder() {
		// 주울이 다른 필터 유형으로 요청을 보내야 하는 순서를 나타내는 정수 값을 반환한다.
		return FILTER_ORDER;
	}

	@Override
	public boolean shouldFilter() {
		// 필터의 활성화 여부를 나타내는 boolean 값을 반환한다.
		return SHOULD_FILTER;
	}
	
	@Override
	public Object run() throws ZuulException {
		// TODO 서비스가 필터를 통과 할때 마다 실행되는 코드. 
		// 현재 이 메서드에서는 tmx-correlation-id의 존재여부를 확인하고 없다면 생성하고 
		// tmx-correlation-id HTTP 헤더를 설정한다.
		if(isCorrelationIdPresent()) {
			logger.debug("tmx-correlation-id found in Pre Check Filter: {}.",filterUtils.getCorrelationId());
		}
		else {
			filterUtils.setCorrelationId(generateCorrelationId());
			logger.debug("tmx-correlation-id generated in Pre Check Filter: {}.",filterUtils.getCorrelationId());
		}
		
        RequestContext ctx = RequestContext.getCurrentContext();
        logger.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());
		
		return null;
	}
	
	//Correlation_Id 가 존재 하는지 여부를 확인하는 메서드
	private boolean isCorrelationIdPresent() {
		if(filterUtils.getCorrelationId() != null) {
			return true;
		}
		return false;
	}
	
    private String generateCorrelationId(){
    	//실제로 tmx-correlation-id존재 여부를 확인하고, 없으면 상관관계ID의 GUID 값을 랜덤으로 생성하는 메서드
        return java.util.UUID.randomUUID().toString();
    }


}
