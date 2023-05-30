package com.rest.transaction.proxy;

import com.rest.transaction.model.exceptions.InvalidAccountNumberException;
import com.rest.transaction.repository.AccountDao;
import com.rest.transaction.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

@AllArgsConstructor
@Component
public class AccountProxy {

    private AccountService accountService;

    private AccountDao accountDao;

    public void makeDeposit(int id, int depositAmount) throws InvalidAccountNumberException {

        try{
            accountDao.findById(id).get();
            accountService.makeDeposit(id,depositAmount);
        }catch (Exception e)
        {
            throw new InvalidAccountNumberException("The account its no valid");
        }

    }

    public int getBalance(int id) throws InvalidAccountNumberException {

        try{
            accountDao.findById(id).get();
            return accountService.getBalance(id);

        }catch (Exception e)
        {
            throw new InvalidAccountNumberException("The account its no valid");
        }
    }

    public Future<String> transfer(int originAccountNumber, int destinationAccountNumber, int transferAmount) throws Exception {

        try{
            accountDao.findById(originAccountNumber).get();
            accountDao.findById(destinationAccountNumber).get();
            return accountService.transfer(originAccountNumber,destinationAccountNumber,transferAmount);

        }catch (Exception e)
        {
            throw new InvalidAccountNumberException("The account its no valid");
        }
    }
}
