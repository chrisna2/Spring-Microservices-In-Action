package com.tyn.bnk.utils;

import org.springframework.util.Assert;

//ThreadLocal클래스에 UserContext를 저장하는 데 사용!

/* ThreadLocal : 쓰레드 단위로 로컬 변수를 할당하게 해주는 클래스 (java 1.2 부터)
   ThreadLocal 을 이용하면 쓰레드 영역에 변수를 설정할 수 있기 때문에,
      특정 쓰레드가 실행하는 모든 코드에서 그 쓰레드에 설정된 변수 값을 사용할 수 있게 된다. 
 
 * ThreadLocal의 활용

	ThreadLocal은 한 쓰레드에서 실행되는 코드가 동일한 객체를 사용할 수 있도록 해 주기 때문에 쓰레드와 관련된 코드에서 파라미터를 사용하지 않고 객체를 전파하기 위한 용도로 주로 사용되며, 주요 용도는 다음과 같다.
	
	사용자 인증정보 전파 - Spring Security에서는 ThreadLocal을 이용해서 사용자 인증 정보를 전파한다.
	트랜잭션 컨텍스트 전파 - 트랜잭션 매니저는 트랜잭션 컨텍스트를 전파하는 데 ThreadLocal을 사용한다.
	쓰레드에 안전해야 하는 데이터 보관
	이 외에도 쓰레드 기준으로 동작해야 하는 기능을 구현할 때 ThreadLocal을 유용하게 사용할 수 있다.
	
 * ThreadLocal 사용시 주의 사항
	
	쓰레드 풀 환경에서 ThreadLocal을 사용하는 경우 ThreadLocal 변수에 보관된 데이터의 사용이 끝나면 반드시 해당 데이터를 삭제해 주어야 한다. 그렇지 않을 경우 재사용되는 쓰레드가 올바르지 않은 데이터를 참조할 수 있다.
 */
public class UserContextHolder {
	
	//정적 "ThreadLocal" 변수에 저장되는 UserContext : 
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<UserContext>();
    
    //UserContext 객체를 사용하기 위해 가져오는 getContext메서드
    public static final UserContext getContext(){
        UserContext context = userContext.get();

        if (context == null) {
            context = createEmptyContext();
            userContext.set(context);

        }
        return userContext.get();
    }
    
    public static final void setContext(UserContext context) {
        Assert.notNull(context, "Only non-null UserContext instances are permitted");
        userContext.set(context);
    }

    public static final UserContext createEmptyContext(){
        return new UserContext();
    }
}
