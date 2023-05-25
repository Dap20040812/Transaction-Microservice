package com.rest.transaction.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class TransferDTO {

    @NotNull
    private int origin;

    @NotNull
    private int destination;

    @NotNull
    private int amount;
}
