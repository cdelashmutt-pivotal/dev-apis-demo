package io.pivotal.pa.fiservservicebroker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.model.*;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;

/**
 * Created by cdelashmutt on 9/12/17.
 */
@Service
public class FiservServiceInstanceService
		implements ServiceInstanceService {

	@Value("${service.url:http://fiserv.com}")
	private String serviceUrl = "http://fiserv.com";

	@Override
	public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
		return new CreateServiceInstanceResponse().withDashboardUrl(serviceUrl + "/dashboard.html");
	}

	@Override
	public GetLastServiceOperationResponse getLastOperation(GetLastServiceOperationRequest request) {
		return new GetLastServiceOperationResponse();
	}

	@Override
	public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
		return new DeleteServiceInstanceResponse();
	}

	@Override
	public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
		return new UpdateServiceInstanceResponse();
	}
}
