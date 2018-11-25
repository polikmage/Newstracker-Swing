package org.mpo.newstracker.exception;

public class NoArticlesFoundException extends CustomException {
    public NoArticlesFoundException(String message, Throwable cause, int statusCode) {
        super(message, cause, statusCode);
    }
}
