package com.rest.transaction.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class  DepositDTO {
    @NotNull
    private int accountNumber;

    @NotNull
    private int depositAmount;
}
