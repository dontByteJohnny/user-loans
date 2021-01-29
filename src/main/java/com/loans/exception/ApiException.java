package com.loans.exception;

import lombok.Data;

@Data
public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;
    private final Error response;

    public ApiException(Integer code, String msg) {
        super(msg);
        this.response = new Error(code, msg);
    }

    @Override
    public String toString() {
        return response.toString();
    }
}