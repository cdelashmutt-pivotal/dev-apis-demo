package io.pivotal.pa.servfinservicebroker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by cdelashmutt on 10/5/17.
 */
@Service
public class ServfinServiceInstanceBindingService
		implements ServiceInstanceBindingService {

	@Value("${service.url:http://servfin.com}")
	private String serviceUrl = "http://servfin.com";

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
		Map<String, Object> creds = new HashMap<>();
		creds.put("uri", serviceUrl);
		creds.put("apiToken", UUID.randomUUID().toString());

		return CreateServiceInstanceAppBindingResponse.builder()
				.credentials(creds)
				.build();
	}

	@Override
	public void deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
		//no op
	}
}
