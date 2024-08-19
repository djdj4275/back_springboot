package com.example.ex24_transaction_propagation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

// 외부 트랜잭션 (트랜잭션A)
@Service
public class BuyAndLogServiceImpl implements BuyTicketService{
  
  @Autowired
  BuyTicketService buyTicketService;

  @Autowired
  LogWriteService logWriteService;

  @Autowired
  TransactionTemplate transactionTemplate;

  @Override
  public int buy(String consumerId, int amount, String error) {
    try {

      transactionTemplate.execute(new TransactionCallbackWithoutResult() {
        @Override
        protected void doInTransactionWithoutResult(TransactionStatus arg0) {
          buyTicketService.buy(consumerId, amount, error);
        
          // error값으로 2가 들어오면 일부러 에러 발생
          if (error.equals("2")) {
            int n = 10/0;
          }

          logWriteService.write(consumerId, amount);
        }
      });

      return 1;
    } catch (Exception e) {
      System.out.println("[Transaction Propagation #A] Rollback");
      return 0;
    }
  }
}