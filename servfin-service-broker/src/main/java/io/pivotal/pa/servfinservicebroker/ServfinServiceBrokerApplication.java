package io.pivotal.pa.servfinservicebroker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class ServfinServiceBrokerApplication {

	@Value("${service.url:http://servfin.com}")
	private String serviceUrl = "http://servfin.com";

	public static void main(String[] args) {
		SpringApplication.run(ServfinServiceBrokerApplication.class, args);
	}

	@Bean
	public Catalog catalog() {

		Plan epayPlan = Plan.builder()
				.id("26a119d8-a505-4ab5-9104-77033ed3b187")
				.name("epay")
				.description("ePayments Billing APIs")
				.metadata("costs", getLiteCosts())
				.metadata("bullets", getLiteBullets())
				.build();

		Plan lendingPlan = Plan.builder()
				.id("338923ac-66a2-4f86-8475-058bf6ba4654")
				.name("lending")
				.description("Lending Apis")
				.free(false)
				.metadata("costs", getEnterpriseCosts())
				.metadata("bullets", getEnterpriseBullets())
				.build();

		return Catalog.builder()
				.serviceDefinitions(
						ServiceDefinition.builder()
								.id("servfin-service-broker")
								.name("servfin")
								.description("A service for accessing Servfin APIs")
								.bindable(true)
								.tags("servfin", "api", "financial")
								.plans(epayPlan, lendingPlan)
								.metadata("displayName", "Servfin APIs")
								.metadata("imageUrl", "data:image/png;base64,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")
								.metadata("longDescription", "Access to Servfin's APIs for integration into your custom applications")
								.metadata("providerDisplayName", "Servfin")
								.metadata("documentationUrl", serviceUrl + "/docs/api-guide.html")
								.metadata("supportUrl", "https://pivotal.io/support")
								.build()
				)
				.build();

	}

	private List<Map<String, Object>> getLiteCosts() {
		Map<String, Object> costsMap = new HashMap<>();

		Map<String, Object> amount = new HashMap<>();
		amount.put("usd", 0.0);

		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");

		return Collections.singletonList(costsMap);
	}

	private List<String> getLiteBullets() {
		return Arrays.asList("Entry level APIs",
				"100 API calls per hour");
	}

	private List<Map<String, Object>> getEnterpriseCosts() {
		Map<String, Object> costsMap = new HashMap<>();

		Map<String, Object> amount = new HashMap<>();
		amount.put("usd", 24.99);

		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");

		return Collections.singletonList(costsMap);
	}

	private List<String> getEnterpriseBullets() {
		return Arrays.asList("Enterprise level APIs",
				"100,000 API calls per hour");
	}

}
