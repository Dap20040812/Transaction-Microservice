package com.rest.transaction.service;

import com.rest.transaction.model.Transaction;
import com.rest.transaction.repository.TransactionDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionService {

    private TransactionDao transactionDao;
    public Transaction createTransaction(int origen, int destination, int amount){
        Transaction transaction = Transaction.builder()
                .origen(origen)
                .destination(destination)
                .amount(amount)
                .build();
        return transactionDao.save(transaction);
    }
}
