package com.rest.transaction.dao;

import com.rest.transaction.repository.AccountDao;
import com.rest.transaction.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountDaoTest {
    @Mock
    private AccountRepository accountRepositoryMock;

    @InjectMocks
    private AccountDao accountDao;


    @Test
    public void given_account_id_and_money_when_updateAccount_then_call_repository() {
        int accountId = 1;
        int newMoney = 100;
        accountDao.updateAccount(newMoney, accountId);
        verify(accountRepositoryMock).updateAccount(newMoney, accountId);
    }

    @Test
    public void given_account_id_when_getBalance_then_return_balance() {
        int accountId = 1;
        int balance = 100;
        when(accountRepositoryMock.getBalance(accountId)).thenReturn(balance);

        int result = accountDao.getBalance(accountId);

        verify(accountRepositoryMock).getBalance(accountId);
        assertEquals(balance, result);
    }
}
