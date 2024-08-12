package com.example.ex21_transaction_not.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ex21_transaction_not.DAO.Transaction1DAO;
import com.example.ex21_transaction_not.DAO.Transaction2DAO;

@Service
public class BuyTicketServiceImpl implements BuyTicketService {

  @Autowired
  Transaction1DAO transaction1DAO;

  @Autowired
  Transaction2DAO transaction2DAO;

  // 보통 서비스에서 db에서 매핑되지않는 데이터의 정제나 에러 처리를 해줌(error)
  @Override
  public int buy(String consumerId, int amount, String error) {
    try {
      transaction1DAO.pay(consumerId, amount);

      // 의도적 에러 발생
      if (error.equals("1")) {
        int n = 10 / 0;
      }

      transaction2DAO.pay(consumerId, amount);
      return 1;
    } catch (Exception e) {
      return 0;
    }
  }
}
