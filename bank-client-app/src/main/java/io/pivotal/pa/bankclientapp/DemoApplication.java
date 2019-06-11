package io.pivotal.pa.bankclientapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.pivotal.pa.epayclient.Bill;
import io.pivotal.pa.epayclient.EpayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.commons.CircuitBreakerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper;
	}

}

@RestController
class BillController {

	private Logger log = LoggerFactory.getLogger(BillController.class);

	private EpayClient client;

	private CircuitBreakerFactory cbFactory;

	public BillController(Optional<EpayClient> client, CircuitBreakerFactory cbFactory) {
		client.ifPresent(service -> this.client = service);
		this.cbFactory = cbFactory;
	}

	@GetMapping("bills")
	public Collection<Bill> findByPayorId(@RequestParam("payorId") String payorId) {
		log.debug("Calling client with payorId: {}", payorId);
		return cbFactory.create("findByPayorId").run(() ->
						client.findByPayorId(payorId),
				throwable -> Collections.emptyList());
	}
}