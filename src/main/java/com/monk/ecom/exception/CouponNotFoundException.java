package com.monk.ecom.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CouponNotFoundException extends Exception {

    private String message;

    public CouponNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
