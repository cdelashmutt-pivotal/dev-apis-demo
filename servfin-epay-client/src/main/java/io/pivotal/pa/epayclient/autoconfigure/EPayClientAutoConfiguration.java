package io.pivotal.pa.epayclient.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.pivotal.pa.epayclient.DefaultEpayClient;
import io.pivotal.pa.epayclient.EpayClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
@ConditionalOnMissingBean(EpayClient.class)
@ConditionalOnProperty(
		name = "io.pivotal.pa.epayclient.uri")
@EnableConfigurationProperties(EPayClientProperties.class)
public class EPayClientAutoConfiguration {

	@Autowired
	private EPayClientProperties properties;

	@Bean
	public EpayClient clientBean() {
		return new DefaultEpayClient(getRestTemplateWithHalMessageConverter(),
				properties.getUri(),
				properties.getApiToken());
	}

	private RestTemplate getRestTemplateWithHalMessageConverter() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> existingConverters = restTemplate.getMessageConverters();
		List<HttpMessageConverter<?>> newConverters = new ArrayList<>();
		newConverters.add(getHalMessageConverter());
		newConverters.addAll(existingConverters);
		restTemplate.setMessageConverters(newConverters);
		return restTemplate;
	}

	private HttpMessageConverter getHalMessageConverter() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new Jackson2HalModule());
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
		halConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON));
		halConverter.setObjectMapper(objectMapper);
		return halConverter;
	}

}
