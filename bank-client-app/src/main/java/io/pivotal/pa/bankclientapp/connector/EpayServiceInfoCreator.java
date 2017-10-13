package io.pivotal.pa.bankclientapp.connector;

import org.springframework.cloud.cloudfoundry.CloudFoundryServiceInfoCreator;
import org.springframework.cloud.cloudfoundry.Tags;

import java.util.Map;

/**
 * Created by cdelashmutt on 10/11/17.
 */
public class EpayServiceInfoCreator extends CloudFoundryServiceInfoCreator<EpayServiceInfo> {

	public EpayServiceInfoCreator() {
		super(new Tags("fiserv"));
	}

	@Override
	public EpayServiceInfo createServiceInfo(Map<String, Object> serviceData) {
		String id = getId(serviceData);
		Map<String, Object> credentials = getCredentials(serviceData);

		String apiToken = (String) credentials.get("apiToken");
		String uri = (String) credentials.get("uri");

		return new EpayServiceInfo(id, apiToken, uri);
	}
}
