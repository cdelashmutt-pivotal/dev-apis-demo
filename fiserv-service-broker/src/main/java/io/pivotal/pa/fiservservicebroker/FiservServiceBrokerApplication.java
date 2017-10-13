package io.pivotal.pa.fiservservicebroker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class FiservServiceBrokerApplication {

	@Value("${service.url}")
	private String serviceUrl = "http://fiserv.com";

	public static void main(String[] args) {
		SpringApplication.run(FiservServiceBrokerApplication.class, args);
	}

	@Bean
	public Catalog catalog() {
		return new Catalog(Collections.singletonList(
				new ServiceDefinition(
						"fiserv-service-broker",
						"fiserv",
						"A service for accessing Fiserv APIs",
						true,
						false,
						Arrays.asList(
								new Plan("26a119d8-a505-4ab5-9104-77033ed3b187",
										"epay",
										"ePayments Billing APIs",
										getEpayPlanMetadata()),
								new Plan("338923ac-66a2-4f86-8475-058bf6ba4654",
										"lending",
										"Lending Apis",
										getLendingPlanMetadata())
						),
						Arrays.asList("fiserv", "api", "financial"),
						getServiceDefinitionMetadata(),
						null,
						null)));
	}

	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> sdMetadata = new HashMap<>();
		sdMetadata.put("displayName", "Fiserv APIs");
		sdMetadata.put("imageUrl", "https://www.cuinsight.com/wp-content/uploads/2015/11/FiservLogo_OrangeBk_321x273-321x273.png");
		sdMetadata.put("longDescription", "Access to Fiserv's APIs for integration into your custom applications");
		sdMetadata.put("providerDisplayName", "Fiserv");
		sdMetadata.put("documentationUrl", serviceUrl + "/swagger-ui.html");
		sdMetadata.put("supportUrl", "https://www.fiserv.com/about/contact-us.aspx");
		return sdMetadata;
	}

	private Map<String, Object> getEpayPlanMetadata() {
		Map<String, Object> planMetadata = new HashMap<>();
		planMetadata.put("costs", getLiteCosts());
		planMetadata.put("bullets", getLiteBullets());
		return planMetadata;
	}

	private Map<String, Object> getLendingPlanMetadata() {
		Map<String, Object> planMetadata = new HashMap<>();
		planMetadata.put("costs", getEnterpriseCosts());
		planMetadata.put("bullets", getEnterpriseBullets());
		return planMetadata;
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
