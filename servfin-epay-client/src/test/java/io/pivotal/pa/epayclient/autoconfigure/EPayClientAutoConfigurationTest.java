package io.pivotal.pa.epayclient.autoconfigure;

import io.pivotal.pa.epayclient.DefaultEpayClient;
import io.pivotal.pa.epayclient.EpayClient;
import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

public class EPayClientAutoConfigurationTest {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withConfiguration(AutoConfigurations.of(EPayClientAutoConfiguration.class));

	@Test
	public void noDefaultWithNoProperties() {
		this.contextRunner.run((context) -> assertThat(context).doesNotHaveBean(EpayClient.class));
	}

	@Test
	public void createsWithProperties() {
		String uri = "http://foobar.com";
		String apiToken = "foobar";
		this.contextRunner.withPropertyValues("io.pivotal.pa.epayclient.uri:" + uri, "io.pivotal.pa.epayclient.apiToken:" + apiToken)
				.run((context) -> {
					assertThat(context).hasSingleBean(EpayClient.class);
					DefaultEpayClient client = (DefaultEpayClient) context.getBean(EpayClient.class);
					assertThat(client.getUri()).isEqualTo(uri);
					assertThat(client.getApiToken()).isEqualTo(apiToken);
				});
	}


	@Test
	public void defaultServiceBacksOff() {
		this.contextRunner.withUserConfiguration(UserConfiguration.class)
				.run((context) -> {
					assertThat(context).hasSingleBean(EpayClient.class);
					assertThat(context.getBean(EpayClient.class)).isSameAs(
							context.getBean(UserConfiguration.class).myUserService()
					);
				});
	}

	@Configuration
	static class UserConfiguration {
		@Bean
		public EpayClient myUserService() {
			return new DefaultEpayClient(null, null, null);
		}
	}
}