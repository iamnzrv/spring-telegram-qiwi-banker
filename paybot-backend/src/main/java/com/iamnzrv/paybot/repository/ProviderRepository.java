package com.iamnzrv.paybot.repository;

import com.iamnzrv.paybot.model.qiwi.Provider;
import org.springframework.data.repository.CrudRepository;

public interface ProviderRepository extends CrudRepository<Provider, Long> {
}
