package com.iamnzrv.paybot.repository;

import com.iamnzrv.paybot.model.user.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepository extends CrudRepository<Wallet, Long> {
}