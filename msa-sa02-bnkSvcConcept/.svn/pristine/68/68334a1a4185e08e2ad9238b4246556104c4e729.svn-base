package com.tyn.bnk.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;
import com.tyn.bnk.utils.UserContextHolder;

//히스트릭스 병행성 전략 클래스의 사용자 정의 클래스
public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy{
//HystrixConcurrencyStrategy 상속 : [목적] 해당 클래스를 정의하는 클래스를 만드는 것
	
	//사용자 정의한 클래스를 받을 지역 클래스 변수
	private HystrixConcurrencyStrategy concurrencyStrategy;
	
	//생성자 생성 : 스프링 클라우드가 미리 정의한 병행성 클래스를 현재 이 클래스의 생성자에 전달한다.
	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy concurrencyStrategy) {
		this.concurrencyStrategy = concurrencyStrategy;
	}
	
	/* 스프링 클라우드가 이미 HystrixConcurrencyStrategy를 정의하기 때문에 재정의할 수 있는 모든 메서드는 기존 병행성 전략 메서의 존재 여부를 
	 * 확인한 후 기존 병행성 전력 메서드나 히스트릭스의 병행성 전략 기본 메서드를 호출해야 한다.보안을 처리하는 용도로 이미 사용중인 기존 HystrixConcurrencyStrategy
	 * 를 적절히 호출하기 위해서 사용하는 관례적인 표현이다. 그렇지 않으면 히스트릭스가 보호하는 코드안에서 스프링 보안 컨택스트를 사용하려고 할때 문제가 발생할 수 있다. 
	 */
	@Override
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		// 일부 메서드의 재정의가 필요함 
		return this.concurrencyStrategy != null //현재 클래스 객체가 존재하면
			   ? this.concurrencyStrategy.getBlockingQueue(maxQueueSize) //현재 클래스의 큐 값으로 
			   : super.getBlockingQueue(maxQueueSize); //상속받은 클래스의 큐 값으로 
	}
	//관례적인 표현 : 아래도 마찬가지로 설정함
	@Override
	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
		
		return this.concurrencyStrategy != null
				? this.concurrencyStrategy.getRequestVariable(rv)
				: super.getRequestVariable(rv);
	}
	//관례적인 표현 : 아래도 마찬가지로 설정함
	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, 
											HystrixProperty<Integer> corePoolSize,
											HystrixProperty<Integer> maximumPoolSize, 
											HystrixProperty<Integer> keepAliveTime, 
											TimeUnit unit,
											BlockingQueue<Runnable> workQueue) {
		
		return this.concurrencyStrategy != null
				? this.concurrencyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)
				: super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	//관례적인 표현 : 아래도 마찬가지로 설정하지만 여기에 UserContext를 주입하도록 하는 Callable클래스를 미리 구현해야 한다.
	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return this.concurrencyStrategy != null
				? this.concurrencyStrategy.wrapCallable(new UserContextDelegateCallable<T>(callable, UserContextHolder.getContext()))
				: super.wrapCallable(new UserContextDelegateCallable<>(callable, UserContextHolder.getContext()));
	}
	
	
	
	
}
