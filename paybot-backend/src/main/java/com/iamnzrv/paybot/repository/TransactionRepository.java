package com.iamnzrv.paybot.repository;

import com.iamnzrv.paybot.model.qiwi.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
