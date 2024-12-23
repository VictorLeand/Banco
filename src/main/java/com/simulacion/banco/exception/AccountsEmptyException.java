package com.simulacion.banco.exception;

public class AccountsEmptyException extends RuntimeException{

    public AccountsEmptyException(String message) {
        super(message);
    }
}
