package com.rest.transaction.service;

import com.rest.transaction.model.Account;
import com.rest.transaction.model.User;
import com.rest.transaction.model.enums.AccountType;
import com.rest.transaction.repository.AccountDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@AllArgsConstructor
public class AccountService {

    private AccountDao bd;

    public void makeDeposit(int id, int depositAmount) {

        bd.updateAccount(getBalance(id) + depositAmount , id);

    }

    public int getBalance(int id) {
        return bd.getBalance(id);
    }

    public Future<String> transfer(int originAccountNumber, int destinationAccountNumber, int transferAmount) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<String> future = executor
                .submit(() -> {
                    int originBalance = getBalance(originAccountNumber);
                    if(originBalance >= transferAmount) {
                        makeDeposit(originAccountNumber, -transferAmount);
                        makeDeposit(destinationAccountNumber, transferAmount);
                        return "Transaction completed successfully";
                    }
                    else {
                        return "There's not enough money in your account";
                    }
                });

        executor.shutdown();

        return future;
    }
}

