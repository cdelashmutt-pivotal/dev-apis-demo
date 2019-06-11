package io.pivotal.pa.epayclient;

import java.util.Collection;

/**
 * Created by cdelashmutt on 10/11/17.
 */
public interface EpayClient {

	Collection<Bill> findByPayorId(String payorId);

}
