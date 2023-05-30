package com.rest.transaction.proxy;

import com.rest.transaction.model.Account;
import com.rest.transaction.model.enums.AccountType;
import com.rest.transaction.model.exceptions.InvalidAccountNumberException;
import com.rest.transaction.repository.AccountDao;
import com.rest.transaction.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountProxyTest {


    @InjectMocks
    private AccountProxy accountProxy;
    @Mock
    private AccountService accountService;

    @Mock
    private AccountDao accountDao;


    @Test
    void given_depositAmount_when_makeDeposit_ValidAccount_then_ValidDepositAmount() throws InvalidAccountNumberException {

        int accountId = 1;
        int depositAmount = 100;

        Account account = Account.builder()
                .money(0)
                .id(accountId)
                .type(AccountType.AHORROS)
                .date_created(new Date())
                .build();

        when(accountDao.findById(accountId)).thenReturn(Optional.of(account));

        accountProxy.makeDeposit(accountId, depositAmount);

        verify(accountDao, times(1)).findById(accountId);
        verify(accountService, times(1)).makeDeposit(accountId, depositAmount);
    }

    @Test
    void given_depositAmount_when_makeDeposit_ValidAccount_then_InvalidAccount_ThrowsInvalidAccountNumberException() {

        int accountId = 1;
        int depositAmount = 100;

        when(accountDao.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(InvalidAccountNumberException.class,
                () -> accountProxy.makeDeposit(accountId, depositAmount));
    }

    @Test
    void given_account_id_when_getBalance_ValidAccount_then_ReturnsAccountBalance() throws InvalidAccountNumberException {

        int accountId = 1;
        int balance = 500;
        Account account = Account.builder()
                .money(0)
                .id(accountId)
                .type(AccountType.AHORROS)
                .date_created(new Date())
                .build();

        when(accountDao.findById(accountId)).thenReturn(Optional.of(account));
        when(accountService.getBalance(accountId)).thenReturn(balance);

        int result = accountProxy.getBalance(accountId);

        assertEquals(balance, result);
        verify(accountDao, times(1)).findById(accountId);
        verify(accountService, times(1)).getBalance(accountId);
    }

    @Test
    void getBalance_InvalidAccount_ThrowsInvalidAccountNumberException() {
        int accountId = 1;

        when(accountDao.findById(accountId)).thenReturn(Optional.empty());

        assertThrows(InvalidAccountNumberException.class,
                () -> accountProxy.getBalance(accountId));
    }

    @Test
    void transfer_ValidAccounts_ValidTransferAmount() throws Exception {
        int originAccountNumber = 1;
        int destinationAccountNumber = 2;
        int transferAmount = 200;
        String expectedResult = "Transaction completed successfully";

        Account account = Account.builder()
                .money(200)
                .id(1)
                .type(AccountType.AHORROS)
                .date_created(new Date())
                .build();

        Account account1 = Account.builder()
                .money(0)
                .id(2)
                .type(AccountType.AHORROS)
                .date_created(new Date())
                .build();

        when(accountDao.findById(originAccountNumber)).thenReturn(Optional.of(account));
        when(accountDao.findById(destinationAccountNumber)).thenReturn(Optional.of(account1));
        when(accountService.transfer(originAccountNumber, destinationAccountNumber, transferAmount))
                .thenReturn(CompletableFuture.completedFuture(expectedResult));

        Future<String> result = accountProxy.transfer(originAccountNumber, destinationAccountNumber, transferAmount);

        assertEquals(result.get(), expectedResult);


    }


}