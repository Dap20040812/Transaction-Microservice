package com.rest.transaction.service;

import com.rest.transaction.model.Account;

import java.util.Optional;

public interface AccountBD {

    void updateAccount(int money, int id);

    int getBalance(int id);

    Optional<Account> findById(int id);


}
