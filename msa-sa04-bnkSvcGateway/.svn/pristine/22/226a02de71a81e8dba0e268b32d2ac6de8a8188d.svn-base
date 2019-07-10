package com.tyn.bnk.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

// 트랜잭션과 연관된 모든 로깅을 완료할 최적의 장소!!
@Component
public class PostInjectLogFilter extends ZuulFilter{
	//모든 줄 필터는 ZuulFilter클래스를 확장하고 오버라이드해야 하는 아래 4개의 메소드를 재정의해야 한다.
	
	private static final Logger logger = LoggerFactory.getLogger(PostInjectLogFilter.class);
		
	private static final int FILTER_ORDER = 1; // 요청을 보내야 하는 순서
	private static final boolean SHOULD_FILTER = true;
		
	@Autowired
	FilterUtils filterUtils;
	
	@Override
	public String filterType() {
	// 주울에서 사전(pre)-경로(route)-사후(post) 필터를 지정하는데 사용된다.
		return FilterUtils.POST_FILTER_TYPE;//"post" : 사후 필터임을 나타내는 키워드
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
        RequestContext ctx = RequestContext.getCurrentContext();
        //원래 Http 요청에서 전달된 상관관계ID를 가져와 응답에 삽입한다.
        logger.debug("Adding the correlation id to the outbound headers. {}", filterUtils.getCorrelationId());
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID, filterUtils.getCorrelationId());
        //처음부터 끝까지 주을에 들어오고 나가는 오청 항목을 보여 주기 위해 나가는 요청 URI를 기록한다.
        logger.debug("Completing outgoing request for {}.", ctx.getRequest().getRequestURI());
		return null;
	}


}
