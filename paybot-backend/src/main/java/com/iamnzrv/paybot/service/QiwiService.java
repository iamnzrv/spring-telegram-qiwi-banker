package com.iamnzrv.paybot.service;

import com.iamnzrv.paybot.config.WalletConfig;
import com.iamnzrv.paybot.exceptions.TransactionExceptions;
import com.iamnzrv.paybot.model.qiwi.*;
import com.iamnzrv.paybot.model.user.ApplicationUser;
import com.iamnzrv.paybot.model.user.Wallet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QiwiService {
  private static final String QIWI_BASE_URL = "https://edge.qiwi.com/";
  private final String WALLET_NUMBER;
  private final String WALLET_TOKEN;

  private final RestTemplate restTemplate;
  private final PaymentService paymentService;
  private final ApplicationUserService applicationUserService;

  private static final String SUCCESS_STATUS = "SUCCESS";
  private static final String IN_TYPE = "IN";

  public QiwiService(
      RestTemplateBuilder restTemplateBuilder,
      WalletConfig walletConfig,
      ThreadPoolTaskExecutor taskExecutor,
      PaymentService paymentService,
      ApplicationUserService applicationUserService
  ) {
    this.WALLET_NUMBER = walletConfig.getNumber();
    this.WALLET_TOKEN = walletConfig.getToken();
    this.restTemplate = restTemplateBuilder.build();
    this.paymentService = paymentService;
    this.applicationUserService = applicationUserService;

    taskExecutor.execute(new Runnable() {
      boolean stop = false;

      @Override
      public void run() {
        while (!stop) {
          try {
            List<Transaction> transactions = registerNewTransactions(getTransactionsAsObject());
            updateUsersWallets(transactions);
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            System.err.println(e.getMessage());
            stop = true;
          }
        }
      }

      public void kill() {
        stop = true;
      }
    });
  }

  private void updateUsersWallets(List<Transaction> transactions) {
    transactions.forEach(transaction -> {
      String username = transaction.getComment();
      Optional<ApplicationUser> userOptional = applicationUserService.loadAccountByUsername(username);
      if (userOptional.isPresent()) {
        Wallet wallet = userOptional.get().getWallet();
        wallet.addTransaction(transaction);
        wallet.setMoneyAmount(wallet.getMoneyAmount() + transaction.getSum().getAmount());
        paymentService.updateWalletById(wallet.getId(), wallet);
      } else {
        try {
          transaction.setError("User did not specify login");
          paymentService.updateTransactionById(transaction.getTxnId(), transaction);
        } catch (TransactionExceptions.TransactionNotFoundException ex) {
          ex.printStackTrace();
        }
      }
    });
  }

  private List<Transaction> registerNewTransactions(TransactionList transactionList) {
    if (transactionList == null) return Collections.emptyList();
    return Arrays.stream(transactionList.getData())
        .filter(transaction -> transaction.getStatus().equals(SUCCESS_STATUS) && transaction.getType().equals(IN_TYPE))
        .map(transaction -> {
          Optional<Transaction> transactionInDb = paymentService.loadTransactionByTxnId(transaction.getTxnId());
          if (transactionInDb.isEmpty()) {
            Commission commission = paymentService.saveCommission(transaction.getCommission());
            Provider provider = paymentService.saveProvider(transaction.getProvider());
            Sum sum = paymentService.saveSum(transaction.getSum());
            Total total = paymentService.saveTotal(transaction.getTotal());
            transaction.setCommission(commission);
            transaction.setProvider(provider);
            transaction.setSum(sum);
            transaction.setTotal(total);
            return paymentService.saveTransaction(transaction);
          } else {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private TransactionList getTransactionsAsObject() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
    Date endDate = new Date();
    Date startDate = new Date();
    startDate.setTime(endDate.getTime() - 10000);

    Map<String, String> requestParams = new HashMap<>();
    requestParams.put("rows", "50");
    requestParams.put("startDate", sdf.format(startDate));
    requestParams.put("endDate", sdf.format(endDate));

    String url = requestParams.keySet().stream()
        .map(key -> key + "=" + requestParams.get(key))
        .collect(Collectors.joining("&",
            QIWI_BASE_URL
                + "payment-history/v2/persons/"
                + WALLET_NUMBER
                + "/payments?", "")
        );

    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(WALLET_TOKEN);
    HttpEntity<String> entity = new HttpEntity<>("body", headers);
    ResponseEntity<TransactionList> response = restTemplate.exchange(url, HttpMethod.GET, entity, TransactionList.class);
    if (response.getStatusCode() == HttpStatus.OK) {
      return response.getBody();
    } else {
      return null;
    }
  }
}
