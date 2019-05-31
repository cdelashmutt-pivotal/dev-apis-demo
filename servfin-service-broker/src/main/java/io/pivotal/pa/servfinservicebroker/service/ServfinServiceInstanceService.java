package io.pivotal.pa.servfinservicebroker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.model.instance.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

/**
 * Created by cdelashmutt on 9/12/17.
 */
@Service
public class ServfinServiceInstanceService
		implements ServiceInstanceService {

	@Value("${service.url:http://servfin.com}")
	private String serviceUrl = "http://servfin.com";

	@Override
	public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
		return CreateServiceInstanceResponse.builder()
				.dashboardUrl(serviceUrl + "/dashboard.html")
				.build();
	}

	@Override
	public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
		return GetLastServiceOperationResponse.builder()
				.build();
	}

	@Override
	public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
		return DeleteServiceInstanceResponse.builder()
				.build();
	}

	@Override
	public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
		return UpdateServiceInstanceResponse.builder()
				.build();
	}
}
