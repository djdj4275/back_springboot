package com.example.ex24_transaction_propagation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
// import org.springframework.transaction.PlatformTransactionManager;
// import org.springframework.transaction.TransactionDefinition;
// import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.ex24_transaction_propagation.dao.Transaction1DAO;
import com.example.ex24_transaction_propagation.dao.Transaction2DAO;

@Service
public class BuyTicketServiceImpl implements BuyTicketService {

    @Autowired
    Transaction1DAO transaction1DAO;

    @Autowired
    Transaction2DAO transaction2DAO;

    @Autowired
    TransactionTemplate transactionTemplate;
    
    @Override
    // @Transactional(propagation = Propagation.REQUIRED)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int buy(String consumerId, int amount, String error) {

        try {
            transactionTemplate.execute(
                new TransactionCallbackWithoutResult() {

                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus arg0) {
                        transaction1DAO.pay(consumerId, amount);
            
                        // 의도적 에러
                        if (error.equals("1")) {
                            int n = 10 / 0;
                        }
            
                        transaction2DAO.pay(consumerId, amount);
                    }
                }
            );
            return 1;
        } catch(Exception e) {
            System.out.println("[Transaction Propagation #B] Rollback");
            return 0;
        }

    }
}
