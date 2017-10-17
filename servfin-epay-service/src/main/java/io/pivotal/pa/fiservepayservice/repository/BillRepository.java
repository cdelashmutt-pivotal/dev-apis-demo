package io.pivotal.pa.fiservepayservice.repository;

import io.pivotal.pa.fiservepayservice.model.Bill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by cdelashmutt on 10/2/17.
 */
public interface BillRepository extends CrudRepository<Bill, String> {
	List<Bill> findByPayorId(@RequestParam("payorId") @Param("payorId") String payorId);
}
