
package com.tyn.bnk.client;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.tyn.bnk.utils.UserContext;

//대안 1 - restTemplate
@Component
public class ConceptRestClient {

	@Autowired
	RestTemplate restTemplate;

	/* 로컬에서 실행하기
	  	
		1.Zuul 및 Eureka를 포함하여 로컬에서 모든 서비스를 실행하는 경우 8 장에서 Zuul 서버를 Eureka에 등록하지 않습니다.
		(아마 당신은 할 수 있습니다, 나는 이것을 끝내지 않습니다 .Zuul은 오래 실행되는 서비스이므로 Eureka에 등록하지 않을 것입니다).
		따라서 소스 코드에서 응용 프로그램을 실행중인 로컬 호스트 나 IP 주소를 가리켜 야합니다. 
		Docker에서 동일한 코드를 실행하면 Docker는 문제의 컨테이너로 해석 할 내부 DNS 서비스를 실행합니다.
		로컬에서 실행하기위한 몇 가지 선택을해야합니다.
			1.Zuul 서비스를 Eureka에 등록하십시오. 확실하지 않지만 작동해야합니다.
			2.코드에서 Zuul 서비스의 로컬 IP 주소를 참조하십시오. 이 경우 표준 Spring RestTemplate을 사용하여 Eureka에 대한 서비스를 검색하지 않도록하십시오.
			3.코드에 직접 IP를 임베드하고 싶지 않다면 로컬 호스트 파일을 IP에 하드 맵 zuulservice로 설정할 수 있습니다 (IP가 변경되지 않는 한)
		
		2.주소 서비스가 유레카 서비스에 등록되기 때문에 주소 서비스가있는 코드가 작동합니다. RestTemplate은 Eureka에 대한 주소 서비스를 검색하여 적절한 IP 주소로 확인합니다.
	 */
	
	private static final Logger logger = LoggerFactory.getLogger(ConceptRestClient.class);
	
	public Map getEmpInfo(String emp_no){
		ResponseEntity<Map> restExchange = restTemplate.exchange(
				//"http://concept/emp/{emp_no}", //concept은 서비스 아이디가 아니라 docker-compose.yml에서 설정한 서버의 명칭이다 <중요!>
				"http://localhost:5555/bnk/c/emp/{emp_no}",
				//localhost:5555
				HttpMethod.GET, 
				null,
				Map.class,
				emp_no);
		return restExchange.getBody();
	} 
	
	// ↕ 이전 버전 을 변형한 것이나 결과는 똑같다. 이제 부터는 모두 httpheader에 Authritication이 없으면 안된다.
	
	@Autowired
	OAuth2RestTemplate oauth2RestTemplate;
	//OAuth2RestTemplate 표준 restTemplate에 대한 드롭인 대체품으로 Oauth2 액세스 토큰에 전파를 처리한다. 
	//그러니까 위에 역활을 중복으러 처리 하면서  Oauth2 액세스 토큰에 전파를 추가로 처리해준다. 
	
	public HashMap getOauth2EmpInfo(String emp_no){
		
		logger.debug("In Licensing Service.getOrganization: {}", UserContext.getCorrelationId());
		
		ResponseEntity<HashMap> restExchange = restTemplate.exchange(
				"http://localhost:5555/bnk/c/emp/{emp_no}", //로컬에서 테스트를 할 경우 localhost:5555로 통일함
				//"http://zuulserver:5555/bnk/c/emp/{emp_no}", //이건 docker에 이미지를 올릴 경우..
				HttpMethod.GET,
				null,
				HashMap.class, //HashMap<String,String> 이건 안되네 ;;
				emp_no);
		
		return restExchange.getBody();
	}
	
	
	
	
}
