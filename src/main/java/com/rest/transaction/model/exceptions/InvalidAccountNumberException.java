package com.rest.transaction.model.exceptions;

public class InvalidAccountNumberException  extends  Exception{

    public InvalidAccountNumberException(String statement){
        super(statement);
    }
}
