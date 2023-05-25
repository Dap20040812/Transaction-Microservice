package com.rest.transaction.repository;

import com.rest.transaction.model.Transaction;
import com.rest.transaction.service.TransactionBD;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionDao implements TransactionBD {

    private TransactionRepository transactionRepository;
    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
