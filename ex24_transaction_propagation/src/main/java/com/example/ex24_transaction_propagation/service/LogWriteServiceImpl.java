package com.example.ex24_transaction_propagation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ex24_transaction_propagation.dao.Transaction3DAO;

@Service
public class LogWriteServiceImpl implements LogWriteService {

    @Autowired
    Transaction3DAO trasaction3DAO;
    
    @Override
    public int write(String consumerId, int amount) {
        try {
            trasaction3DAO.pay(consumerId, amount);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
