package com.iamnzrv.paybot.repository;

import com.iamnzrv.paybot.model.qiwi.Commission;
import org.springframework.data.repository.CrudRepository;

public interface CommissionRepository extends CrudRepository<Commission, Long> {
}
