package com.example.ex23_transaction_template.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.ex23_transaction_template.DAO.Transaction1DAO;
import com.example.ex23_transaction_template.DAO.Transaction2DAO;

@Service
public class BuyTicketServiceImpl implements BuyTicketService {

  @Autowired
  Transaction1DAO transaction1DAO;

  @Autowired
  Transaction2DAO transaction2DAO;

  @Autowired
  TransactionTemplate transactionTemplate;

  // 서비스역할 : 보통 서비스에서 db에서 매핑되지않는 데이터의 정제나 에러 처리를 해줌(error), 트랜잭션 처리
  @Override
  public int buy(String consumerId, int amount, String error) {

    // 트랜잭션이란 여러가지의 로직이 차례차례 진행되면서 에러를 확인후 에러가 나면 모든 로직을 롤백, 나지 않았다면 정상처리를 한꺼번에 해주는것을 말함.

    // 트랜잭션을 사용하는 방식은 manager와 template 두가지 방식이 존재하는데 지금 사용한 방식은 template 방식

    try {
      transactionTemplate.execute(
        new TransactionCallbackWithoutResult() {

          @Override
          protected void doInTransactionWithoutResult(TransactionStatus arg0) {
            transaction1DAO.pay(consumerId, amount);

            // 의도적 에러 발생
            if (error.equals("1")) {
              int n = 10 / 0;
            }

            transaction2DAO.pay(consumerId, amount);
          }
        }
      );

      return 1;
    } catch (Exception e) {

      return 0;
    }
  }
}