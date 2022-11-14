package com.egc.atmservice.domain.exception;

public class SessionException extends Exception {

    public SessionException(ExceptionMassage exceptionMassage) {
        super(exceptionMassage.name());
    }
}
