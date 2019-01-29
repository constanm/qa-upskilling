package com.selenium.test.exceptions;

public class WrongPageException extends RuntimeException {

    public WrongPageException(String message) {

        super(message);
    }
}