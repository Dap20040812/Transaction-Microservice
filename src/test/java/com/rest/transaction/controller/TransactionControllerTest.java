package com.rest.transaction.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.transaction.service.AccountService;
import com.rest.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void given_a_put_request_when_invoke_deposit_then_return_the_success_message() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("accountNumber", 1)
                .put("depositAmount", 100);
        String jsonString = mapper.writeValueAsString(json);

        mockMvc.perform(put("/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit completed successfully"));
    }
    @Test
    public void given_a_put_request_when_invoke_transfer_and_it_is_possible_to_do_then_return_the_success_message() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("origin", 12345)
                .put("destination", 54321)
                .put("amount",100);
        String jsonString = mapper.writeValueAsString(json);
        String successMessage = "Transaction completed successfully";
        when(accountService.transfer(12345, 54321,100)).thenReturn(CompletableFuture.completedFuture(successMessage));

        mockMvc.perform(put("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string(successMessage));

        verify(transactionService, times(1)).createTransaction(12345,54321,100);
    }

    @Test
    public void given_a_put_request_when_invoke_transfer_and_it_is_not_possible_to_do_then_return_the_success_message() throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.createObjectNode()
                .put("origin", 12345)
                .put("destination", 54321)
                .put("amount",100);
        String jsonString = mapper.writeValueAsString(json);
        String successMessage = "Transaction completed successfully";
        when(accountService.transfer(12345, 54321,100)).thenReturn(CompletableFuture.completedFuture("Insufficient funds in the source account"));

        mockMvc.perform(put("/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("Insufficient funds in the source account"));

    }

}
