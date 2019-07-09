package com.tyn.bnk.hystrix;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;

@Configuration
public class ThreadLocalConfig {

	@Autowired(required = false)
	//구성 객체가 생성될 때 기존 HystrixConcurrencyStrategy와 자동 연결 한다
	private HystrixConcurrencyStrategy concurrencyStrategy;
	
	@PostConstruct
	public void init() {
		//기존의 히스트릭스 플러그인의 레퍼런스 유지
		HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
        HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
        HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
        HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
        //↑새로운 병행성 전략을 등록하기 때문에 모든 히스트릭스 컴포넌트를 가져와 플러그인을 재설정한다.
       
        HystrixPlugins.reset();
        //이제 HystrixConcurrencyStrategy(ThreadLocalAwareStratagy)를 등록한다..
        HystrixPlugins.getInstance().registerConcurrencyStrategy(new ThreadLocalAwareStrategy(concurrencyStrategy));
        
        //히스트릭스 플러그인이 사용하는 모든 히스트릭스 컴포넌트를 재등록한다.
        HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
        HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
        HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
        HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
        
        /*
         2019-07-08 03:04:27.951  INFO 15528 --- [nio-8080-exec-5] com.tyn.bnk.utils.UserContextFilter      : Harang.UserContextFilter Correlation id: test-correlation-id
		 2019-07-08 03:04:27.963  INFO 15528 --- [nio-8080-exec-5] c.t.b.controller.SimpleClientController  : Harang.SimpleClientController Correlation id: test-correlation-id
		 2019-07-08 03:04:27.972  INFO 15528 --- [radeTreadPool-4] c.t.bnk.service.impl.SimpleServiceImpl   : Harang.SimpleServiceImpl Correlation Id:test-correlation-id <이거 띄울려고함>
        	=> 이 모든게 @서비스에도 Correlation Id의 값을 받아오기 위한 것 ㅠㅠ
         *
         */
        
        
	}
	
}
