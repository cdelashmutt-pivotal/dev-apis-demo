package io.pivotal.pa.bankclientapp.connector;

import org.springframework.cloud.service.BaseServiceInfo;

/**
 * Created by cdelashmutt on 10/11/17.
 */
public class EpayServiceInfo extends BaseServiceInfo {

	private String apiToken;
	private String uri;

	public EpayServiceInfo(String id, String apiToken, String uri) {
		super(id);
		this.apiToken = apiToken;
		this.uri = uri;
	}

	public String getApiToken() {
		return apiToken;
	}

	public String getUri() {
		return uri;
	}
}
