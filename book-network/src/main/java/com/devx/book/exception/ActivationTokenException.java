package com.devx.book.exception;

public class ActivationTokenException extends RuntimeException {

    private ActivationTokenException(String message) {
        super(message);
    }
}
