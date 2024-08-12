package com.example.ex22_transaction_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.example.ex22_transaction_manager.DAO.Transaction1DAO;
import com.example.ex22_transaction_manager.DAO.Transaction2DAO;

@Service
public class BuyTicketServiceImpl implements BuyTicketService {

  @Autowired
  Transaction1DAO transaction1DAO;

  @Autowired
  Transaction2DAO transaction2DAO;

  @Autowired
  PlatformTransactionManager transactionManager;

  @Autowired
  TransactionDefinition transactionDefinition;

  // 서비스역할 : 보통 서비스에서 db에서 매핑되지않는 데이터의 정제나 에러 처리를 해줌(error), 트랜잭션 처리
  @Override
  public int buy(String consumerId, int amount, String error) {

    // 트랜잭션이란 여러가지의 로직이 차례차례 진행되면서 에러를 확인후 에러가 나면 모든 로직을 롤백, 나지 않았다면 정상처리를 한꺼번에 해주는것을 말함.
    TransactionStatus status = transactionManager.getTransaction(transactionDefinition);

    try {
      transaction1DAO.pay(consumerId, amount);

      // 의도적 에러 발생
      if (error.equals("1")) {
        int n = 10 / 0;
      }

      transaction2DAO.pay(consumerId, amount);

      transactionManager.commit(status); // 에러가 발생하지 않으면 최종적으로 위 db관련 로직 다 커밋(정상처리)

      return 1;
    } catch (Exception e) {

      transactionManager.rollback(status); // 에러 발생하면 db관련 로직 전부 롤백 (취소처리)

      return 0;
    }
  }
}
