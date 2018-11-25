package org.mpo.newstracker.exception;

public class CustomException extends Exception{
    private int statusCode;

    CustomException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

}
