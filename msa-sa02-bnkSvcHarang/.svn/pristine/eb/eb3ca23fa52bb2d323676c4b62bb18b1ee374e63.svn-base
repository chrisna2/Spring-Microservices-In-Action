package com.tyn.bnk.hystrix;

import java.util.concurrent.Callable;

import com.tyn.bnk.utils.UserContext;
import com.tyn.bnk.utils.UserContextHolder;

//UserContext를 주입하도록 하는 Callable클래스
public final class UserContextDelegateCallable<V> implements Callable<V> {

	private final Callable<V> delegate;
	private UserContext originUserContext;
	
	//생성자 생성 : 사용자 정의 callable 클래스에 히스트릭스로 보호된 코드를 호출하는 원본 callable클래스와 부모 스레드에서 받은 UserContext를 전달한다.
	public UserContextDelegateCallable(Callable<V> delegate, UserContext originUserContext) {
		this.delegate = delegate;
		this.originUserContext = originUserContext;
	}
	
	@Override
	// @HystrixCommand 애너테이션이 메서드를 보호하기 전에 호출되는 Call함수다.
	public V call() throws Exception {
		//UserContext가 설정된다.
		//UserContext를 저장하는 ThreadLocal 변수는 히스트릭스가 보호하는 메서드를 실행하는 스레드에 연결된다.
		UserContextHolder.setContext(originUserContext);
		
		try {
			//UserContext가 설정되면 히스트릭스가 보호하는 메서드의 call()메서드를 호출한다.
			return this.delegate.call();
		} finally {
			//어찌되었든 originUserContext는 비워 줘야 된다.
			this.originUserContext = null;
		}
	}
	
	public static <V> Callable<V> create(Callable<V> delegate, UserContext userContext){
		return new UserContextDelegateCallable<>(delegate, userContext);
	}
}
