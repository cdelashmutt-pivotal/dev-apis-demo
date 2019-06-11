package io.pivotal.pa.epayclient.cfenv;

import io.pivotal.cfenv.core.CfCredentials;
import io.pivotal.cfenv.core.CfService;
import io.pivotal.cfenv.spring.boot.CfEnvProcessor;
import io.pivotal.cfenv.spring.boot.CfEnvProcessorProperties;

import java.util.Map;

public class EPayClientCfEnvProcessor implements CfEnvProcessor {

	@Override
	public boolean accept(CfService service) {
		return service.existsByTagIgnoreCase("servfin") ||
				service.existsByLabelStartsWith("servfin");
	}

	@Override
	public void process(CfCredentials cfCredentials, Map<String, Object> properties) {
		properties.put("io.pivotal.pa.epayclient.uri", cfCredentials.getUri("https", "http"));
		properties.put("io.pivotal.pa.epayclient.apiToken", cfCredentials.getString("apiToken"));
	}

	@Override
	public CfEnvProcessorProperties getProperties() {
		return CfEnvProcessorProperties.builder()
				.propertyPrefixes("io.pivotal.pa.epayclient")
				.serviceName("EPayClient")
				.build();
	}
}
