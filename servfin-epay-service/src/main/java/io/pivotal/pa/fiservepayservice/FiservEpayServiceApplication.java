package io.pivotal.pa.fiservepayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EnableSwagger2
@Import(SpringDataRestConfiguration.class)
public class FiservEpayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiservEpayServiceApplication.class, args);
	}

	@Bean
	public Docket billApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(new ApiInfo("Bill Pay APIs", "A set of APIs to manage bills, and payments of those bills.", "1.0.54", null, new Contact("Fiserv", "http://fiserv.com", "epay-api@fiserv.com"), null, null, Collections.emptyList()))
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Repository.class))
				.paths(PathSelectors.any())
				.build();
	}
}
