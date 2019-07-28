package com.tyn.bnk.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    public static final String CORRELATION_ID = "tmx-correlation-id";
    public static final String AUTH_TOKEN     = "tmx-auth-token";
    public static final String USER_ID        = "tmx-user-id";
    public static final String EMP_NO         = "tmx-emp-no";//임의로 변경
    public static final String PRE_FILTER_TYPE = "pre";//사전 필터 클래스
    public static final String POST_FILTER_TYPE = "post";//사후 필터 클래스
    public static final String ROUTE_FILTER_TYPE = "route";//경로 필터 클래스

    public String getCorrelationId(){
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(CORRELATION_ID) !=null) {
            return ctx.getRequest().getHeader(CORRELATION_ID);
        }
        else{
            return  ctx.getZuulRequestHeaders().get(CORRELATION_ID);
        }
    }

    public void setCorrelationId(String correlationId){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID, correlationId);
    }
    /*임의로 변경*/
    public  final String getEmpNo(){
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(EMP_NO) !=null) {
            return ctx.getRequest().getHeader(EMP_NO);
        }
        else{
            return  ctx.getZuulRequestHeaders().get(EMP_NO);
        }
    }
    //임의로 변경
    public void setEmpNo(String empNo){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(EMP_NO,  empNo);
    }
    public final String getUserId(){
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.getRequest().getHeader(USER_ID) !=null) {
            return ctx.getRequest().getHeader(USER_ID);
        }
        else{
            return  ctx.getZuulRequestHeaders().get(USER_ID);
        }
    }
    public void setUserId(String userId){
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(USER_ID,  userId);
    }
    public final String getAuthToken(){
        RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.getRequest().getHeader(AUTH_TOKEN);
    }
    //임의로 변경
    public String getMId(){
        RequestContext ctx = RequestContext.getCurrentContext();
        if (ctx.get("m_id")==null) return "";
        return ctx.get("m_id").toString();
    }


}
