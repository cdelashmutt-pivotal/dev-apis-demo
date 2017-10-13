package io.pivotal.pa.bankclientapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.pivotal.pa.bankclientapp.connector.Bill;
import io.pivotal.pa.bankclientapp.connector.EpayClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@SpringBootApplication
@EnableCircuitBreaker
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

	Logger log = LoggerFactory.getLogger(BillController.class);

	@Autowired(required = false)
	EpayClient client;

	@GetMapping("bills")
	@HystrixCommand(fallbackMethod = "defaultBills")
	public Collection<Bill> findByPayorId(@RequestParam("payorId") String payorId) {
		log.debug("Calling client with payorId: {}", payorId);
		return client.findByPayorId(payorId);
	}

	public Collection<Bill> defaultBills(String payorId) {
		return Collections.emptyList();
	}
}