package com.exceptions;

public class ExceededMaxSizeException extends Exception {
    public ExceededMaxSizeException(String msg) {
        super(msg);
    }
}