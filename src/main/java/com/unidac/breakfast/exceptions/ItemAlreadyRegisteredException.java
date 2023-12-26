package com.unidac.breakfast.exceptions;

public class ItemAlreadyRegisteredException extends RuntimeException {
    public ItemAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
