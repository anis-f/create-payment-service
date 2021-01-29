package com.stuff.payment.common.exception;

public class Exception {

    String error;

    public Exception(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
