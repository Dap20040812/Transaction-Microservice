package com.rest.transaction.service;

import com.rest.transaction.model.Transaction;
import com.rest.transaction.repository.TransactionDao;
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
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionDao transactionDaoMock;

    @Test
    public void given_origin_destination_and_amount_when_createTransaction_then_transaction_should_be_created() {
        int origin = 1;
        int destination = 2;
        int amount = 100;
        Transaction transaction = Transaction.builder()
                .origen(origin)
                .destination(destination)
                .amount(amount)
                .build();
        when(transactionDaoMock.save(transaction)).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(origin, destination, amount);

        assertEquals(transaction, result);
        verify(transactionDaoMock).save(transaction);
    }

}
