package io.pivotal.pa.epayclient.cfenv;

import io.pivotal.cfenv.test.AbstractCfEnvTests;
import org.junit.Test;
import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.assertThat;

public class EPayClientCfEnvProcessorTest extends AbstractCfEnvTests {
	@Test
	public void testBasicSpringSecurityProperties() {
		String uri = "http://foobar.com";
		String apiToken = "atoken";
		mockVcapServices(getServicesPayload(readTestDataFile("test-cf-env.json")
				.replace("$uri", uri)
				.replace("$apiToken", apiToken)));
		Environment env = getEnvironment();

		assertThat(env.getProperty("io.pivotal.pa.epayclient.uri"))
				.isEqualTo(uri);

		assertThat(env.getProperty("io.pivotal.pa.epayclient.apiToken"))
				.isEqualTo(apiToken);

	}
}