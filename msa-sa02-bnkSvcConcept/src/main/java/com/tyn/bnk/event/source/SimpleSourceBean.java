package com.tyn.bnk.event.source;

import java.util.HashMap;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.tyn.bnk.utils.UserContext;

@Component
public class SimpleSourceBean {

	private Source source;
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);
	
	@Autowired//스프링 클라우드 스트림은 서비스가 사용할 소스 인터 페이스 구현을 주입한다.
	public SimpleSourceBean(Source source) {
		this.source = source;
	}
	
	 public void publishConceptChange(String action, String empNo) {
		 logger.info("@CoceptSource\n"
		 		+ "Sending Kafka message {}\n"
		 		+ "for emp_no : {}\n",action,empNo);
		 
		 Map<String, Object> msg = new HashMap<String, Object>();
		 
		 msg.put("type", Map.class.getTypeName());
		 msg.put("action", action);
		 msg.put("empNo", empNo);
		 msg.put("correlationId", UserContext.getCorrelationId());
		 
		 //책에서 POJO형을 통해 메세지를 전달 하지만 나는 Map을 통해 메세지가 전달되는 확인을 하고 싶다.
		 
		 source.output()
		 	   .send(MessageBuilder.withPayload(msg).build());
		 		//메세지를 보낼 준비가 되면 Source 클래스에서 정의된 채널에서 send() 메서드를 사용한다.
		 
	 }
		 
		 

	
}
