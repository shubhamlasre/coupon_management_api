package com.monk.ecom.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConditionNotMetException extends Exception {

    private String message;

    public ConditionNotMetException(String message) {
        super(message);
        this.message = message;
    }
}
