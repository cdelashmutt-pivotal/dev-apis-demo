package io.pivotal.pa.epayclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cdelashmutt on 10/12/17.
 */
public class DefaultEpayClient implements EpayClient {

	private RestOperations restOperations;

	private String uri;

	private String apiToken;

	private Logger log = LoggerFactory.getLogger(DefaultEpayClient.class);

	public DefaultEpayClient(RestOperations restOperations, String uri, String apiToken) {
		this.restOperations = restOperations;
		this.uri = uri;
		this.apiToken = apiToken;
	}

	@Override
	public Collection<Bill> findByPayorId(String payorId) {
		UriComponents uriComponents = UriComponentsBuilder
				.fromUriString(String.format("%s/bills/search/findByPayorId", this.uri))
				.queryParam("payorId", payorId)
				.queryParam("apiToken", apiToken)
				.build();
		log.debug("Making request to: {}", uriComponents.toUriString());
		RequestEntity<Void> request = RequestEntity.get(uriComponents.toUri()).accept(MediaTypes.HAL_JSON).build();
		ResponseEntity<PagedResources<Resource<Bill>>> billResponse = restOperations.exchange(uriComponents.toUri(), HttpMethod.GET, request, new TypeReferences.PagedResourcesType<Resource<Bill>>() {
		});
		//(request, new ParameterizedTypeReference<ResponseEntity<Resources<Bill>>>() {}).getBody();
		log.debug("Got resources: {}", billResponse.getBody());

		List<Bill> bills = new ArrayList<>();

		Collection<Resource<Bill>> resources = billResponse.getBody().getContent();
		if (resources != null) resources.forEach(billResource -> bills.add(billResource.getContent()));

		return bills;

	}

	public String getUri() {
		return uri;
	}

	public String getApiToken() {
		return apiToken;
	}
}
