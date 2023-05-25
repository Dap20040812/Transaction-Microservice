package com.rest.transaction.controller;

import com.rest.transaction.controller.dto.DepositDTO;
import com.rest.transaction.controller.dto.TransferDTO;
import com.rest.transaction.model.exceptions.InvalidAccountNumberException;
import com.rest.transaction.proxy.AccountProxy;
import com.rest.transaction.service.AccountService;
import com.rest.transaction.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Future;
@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;
    private AccountProxy accountProxy;

   @PutMapping(path = "/deposit")
    public String depositFunds(@RequestBody DepositDTO depositDTO) throws InvalidAccountNumberException {
        accountProxy.makeDeposit(depositDTO.getAccountNumber(), depositDTO.getDepositAmount());
        return "Deposit completed successfully";
    }

    @PutMapping(path = "/transfer")
    public String transfer(@RequestBody TransferDTO transferDTO) throws Exception {

        Future<String> futureTransfer = accountProxy.transfer(transferDTO.getOrigin(),transferDTO.getDestination(),transferDTO.getAmount());
        String futureResult = futureTransfer.get();
        if(futureResult.equals("Transaction completed successfully"))
            transactionService.createTransaction(transferDTO.getOrigin(),transferDTO.getDestination(),transferDTO.getAmount());
        return futureTransfer.get();
    }



}
