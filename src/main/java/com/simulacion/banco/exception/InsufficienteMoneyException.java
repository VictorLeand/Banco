package com.simulacion.banco.exception;

public class InsufficienteMoneyException extends RuntimeException{

    public InsufficienteMoneyException(String message) {
        super(message);
    }
}
