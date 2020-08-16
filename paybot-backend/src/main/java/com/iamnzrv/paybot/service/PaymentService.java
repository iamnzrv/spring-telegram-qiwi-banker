package com.iamnzrv.paybot.service;

import com.iamnzrv.paybot.exceptions.TransactionExceptions;
import com.iamnzrv.paybot.model.qiwi.*;
import com.iamnzrv.paybot.model.user.Wallet;
import com.iamnzrv.paybot.repository.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {
  private final CommissionRepository commissionRepository;
  private final SumRepository sumRepository;
  private final TotalRepository totalRepository;
  private final ProviderRepository providerRepository;
  private final TransactionRepository transactionRepository;
  private final WalletRepository walletRepository;

  public PaymentService(
      CommissionRepository commissionRepository,
      SumRepository sumRepository,
      TotalRepository totalRepository,
      ProviderRepository providerRepository,
      TransactionRepository transactionRepository,
      WalletRepository walletRepository
  ) {
    this.commissionRepository = commissionRepository;
    this.sumRepository = sumRepository;
    this.totalRepository = totalRepository;
    this.providerRepository = providerRepository;
    this.transactionRepository = transactionRepository;
    this.walletRepository = walletRepository;
  }

  public Commission saveCommission(Commission commission) {
    return commissionRepository.save(commission);
  }

  public Sum saveSum(Sum sum) {
    return sumRepository.save(sum);
  }

  public Total saveTotal(Total total) {
    return totalRepository.save(total);
  }

  public Provider saveProvider(Provider provider) {
    return providerRepository.save(provider);
  }

  public Transaction saveTransaction(Transaction transaction) {
    return transactionRepository.save(transaction);
  }

  public Wallet saveWallet(Wallet wallet) {
    return walletRepository.save(wallet);
  }

  public Optional<Transaction> loadTransactionByTxnId(Long txnId) {
    return transactionRepository.findById(txnId);
  }

  public Transaction updateTransactionById(Long id, Transaction newTransaction) {
    Optional<Transaction> TransactionOptional = transactionRepository.findById(id);
    if (TransactionOptional.isPresent()) {
      newTransaction.setTxnId(TransactionOptional.get().getTxnId());
      return transactionRepository.save(newTransaction);
    } else {
      throw new TransactionExceptions.TransactionNotFoundException();
    }
  }

  public Wallet updateWalletById(Long id, Wallet newWallet) {
    Optional<Wallet> walletOptional = walletRepository.findById(id);
    if (walletOptional.isPresent()) {
      newWallet.setId(walletOptional.get().getId());
      return walletRepository.save(newWallet);
    } else {
      throw new TransactionExceptions.TransactionNotFoundException();
    }
  }
}
