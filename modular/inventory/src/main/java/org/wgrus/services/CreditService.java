package org.wgrus.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CreditService {

	public String check(String customerId) {
		RestTemplate t = new RestTemplate();
		HttpEntity<String> requestEntity = new HttpEntity<String>("");
		ResponseEntity<String> r = t.exchange("http://wgrus-billing.cloudfoundry.com/authorize/" + customerId,
				HttpMethod.GET, requestEntity, String.class);
		return r.getBody();
	}
}
