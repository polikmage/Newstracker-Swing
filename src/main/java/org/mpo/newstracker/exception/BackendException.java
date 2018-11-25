package org.mpo.newstracker.exception;

public class BackendException extends CustomException {

    public BackendException(String message, Throwable cause, int statusCode){
        super(message,cause,statusCode);
    }


}
