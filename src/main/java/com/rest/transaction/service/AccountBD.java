package com.rest.transaction.service;

import com.rest.transaction.model.Account;

public interface AccountBD {

    void updateAccount(int money, int id);

    int getBalance(int id);




}
