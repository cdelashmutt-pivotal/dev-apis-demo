package io.pivotal.pa.fiservservicebroker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cdelashmutt on 10/5/17.
 */
@Service
public class FiservServiceInstanceBindingService
		implements ServiceInstanceBindingService {

	@Value("${service.url}")
	private String serviceUrl = "http://fiserv.com";

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
		Map<String, Object> creds = new HashMap<>();
		creds.put("uri", serviceUrl);
		creds.put("apiToken", UUID.randomUUID().toString());

		return new CreateServiceInstanceAppBindingResponse().withCredentials(creds);
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		//no op
	}
}
