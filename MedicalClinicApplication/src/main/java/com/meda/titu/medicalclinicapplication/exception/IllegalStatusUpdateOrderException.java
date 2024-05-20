package com.meda.titu.medicalclinicapplication.exception;

public class IllegalStatusUpdateOrderException extends RuntimeException {
    public IllegalStatusUpdateOrderException(String message) {
        super(message);
    }
}
