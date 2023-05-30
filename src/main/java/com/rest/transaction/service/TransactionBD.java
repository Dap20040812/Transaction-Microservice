package com.rest.transaction.service;

import com.rest.transaction.model.Transaction;

public interface TransactionBD {

    Transaction save(Transaction transaction);
}
