package com.rest.transaction.controller;

import com.rest.transaction.controller.dto.DepositDTO;
import com.rest.transaction.controller.dto.TransferDTO;
import com.rest.transaction.service.AccountService;
import com.rest.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;

@RestController
@AllArgsConstructor
public class TransactionController {

    private AccountService accountService;
    private TransactionService transactionService;

   @PutMapping(path = "/deposit")
    public String depositFunds(@RequestBody DepositDTO depositDTO) {
        accountService.makeDeposit(depositDTO.getAccountNumber(), depositDTO.getDepositAmount());
        return "Deposit completed successfully";
    }

    @PutMapping(path = "/transfer")
    public String transfer(@RequestBody TransferDTO transferDTO) throws Exception {

        Future<String> futureTransfer = accountService.transfer(transferDTO.getOrigin(),transferDTO.getDestination(),transferDTO.getAmount());
        String futureResult = futureTransfer.get();
        if(futureResult.equals("Transaction completed successfully"))
            transactionService.createTransaction(transferDTO.getOrigin(),transferDTO.getDestination(),transferDTO.getAmount());
        return futureTransfer.get();
    }



}
