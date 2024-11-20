package com.monk.ecom.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidInputException extends Exception {

    private String message;

    public InvalidInputException(String message) {
        super(message);
        this.message = message;
    }
}
