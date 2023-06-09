package com.rest.transaction.repository;

import com.rest.transaction.model.Account;
import com.rest.transaction.service.AccountBD;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class  AccountDao implements AccountBD {

    private AccountRepository accountRepository;

    @Override
    public void updateAccount(int money, int id) {
        accountRepository.updateAccount(money,id);
    }

    @Override
    public int getBalance(int id) {
        return accountRepository.getBalance(id);
    }

    @Override
    public Optional<Account> findById(int id) {
        return accountRepository.findById(id);
    }
}
