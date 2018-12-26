package org.mpo.newstracker.exception;

public class NoWatchdogFoundException extends CustomException {
    public NoWatchdogFoundException(String message, Throwable cause, int statusCode){
        super(message, cause, statusCode);
    }
}
