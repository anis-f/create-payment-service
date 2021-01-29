package com.stuff.payment.common.errorhandler;

public class InvalidInputException extends Throwable {
    public InvalidInputException(String message) {
        super(message);
    }
}
