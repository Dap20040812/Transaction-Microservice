package com.rest.transaction.service;

import com.rest.transaction.repository.AccountDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountDao accountDaoMock;


    @Test
    public void given_account_id_and_deposit_amount_when_makeDeposit_then_account_balance_should_be_updated() {
        int accountId = 1;
        int depositAmount = 100;
        int currentBalance = 500;
        when(accountDaoMock.getBalance(accountId)).thenReturn(currentBalance);

        accountService.makeDeposit(accountId, depositAmount);

        Mockito.verify(accountDaoMock, times(1)).updateAccount(currentBalance + depositAmount, accountId);
    }

    @Test
    public void given_origin_account_number_destination_account_number_and_transfer_amount_when_transfer_then_balance_should_be_updated() throws Exception {
        int originAccountNumber = 1;
        int destinationAccountNumber = 2;
        int transferAmount = 100;
        int originAccountBalance = 500;
        when(accountDaoMock.getBalance(originAccountNumber)).thenReturn(originAccountBalance);

        String result = accountService.transfer(originAccountNumber, destinationAccountNumber, transferAmount).get();
        verify(accountDaoMock, times(2)).getBalance(originAccountNumber);
        verify(accountDaoMock, times(1)).updateAccount(originAccountBalance - transferAmount, originAccountNumber);
        verify(accountDaoMock, times(1)).updateAccount(transferAmount, destinationAccountNumber);
        assertEquals("Transaction completed successfully", result);
    }

    @Test
    public void given_origin_account_number_destination_account_number_and_transfer_amount_when_transfer_found_are_invalid_then_balance_should_be_updated() throws Exception {
        int originAccountNumber = 1;
        int destinationAccountNumber = 2;
        int transferAmount = 600;
        int originAccountBalance = 500;
        when(accountDaoMock.getBalance(originAccountNumber)).thenReturn(originAccountBalance);

        String result = accountService.transfer(originAccountNumber, destinationAccountNumber, transferAmount).get();
        assertEquals("There's not enough money in your account", result);
    }

}
