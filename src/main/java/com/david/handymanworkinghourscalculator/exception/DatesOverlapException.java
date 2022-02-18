package com.david.handymanworkinghourscalculator.exception;

public class DatesOverlapException extends RuntimeException{
    public DatesOverlapException(String message) {
        super(message);
    }
}
