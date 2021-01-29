package com.stuff.payment.common.errorhandler;

public class BusinessException extends Throwable {
    public BusinessException(String message) {
        super(message);
    }
}
