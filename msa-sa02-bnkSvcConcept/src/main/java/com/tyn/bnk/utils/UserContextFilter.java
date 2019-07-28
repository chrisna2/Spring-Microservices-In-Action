package com.tyn.bnk.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

//필요한 Http 헤더값을 자바 클래스인 UserContext에 매핑하는 데 사용된다.
@Component
public class UserContextFilter implements Filter {
	//필터는 스프링 @Component 에너테이션을 사용하고 javax.servlet.Filter인터페이스를 구현해 스프링에 등록되고 실현된다.
    private static final Logger logger = LoggerFactory.getLogger(UserContextFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        //Http 호출 헤더에서 검색한 값을 UserContextHolder의 UserContext에 저장한다.
        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID));
        	//필터는 헤더에서 상관관계ID를 검색해서 UserContext 클래스에 설정한다.
        UserContextHolder.getContext().setUserId(httpServletRequest.getHeader(UserContext.USER_ID));
        UserContextHolder.getContext().setAuthToken(httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
        UserContextHolder.getContext().setEmpNo(httpServletRequest.getHeader(UserContext.EMP_NO));

        logger.info("Harang.UserContextFilter Correlation id: {}", UserContextHolder.getContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}