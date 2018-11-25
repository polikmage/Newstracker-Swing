package org.mpo.newstracker.exception;

public class CountryNotFoundException extends CustomException{
    public CountryNotFoundException(String message, Throwable cause, int statusCode) {
        super(message, cause, statusCode);
    }
}
