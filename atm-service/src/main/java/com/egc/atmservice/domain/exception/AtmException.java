package com.egc.atmservice.domain.exception;

import lombok.Getter;

@Getter
public class AtmException extends Exception {

    public AtmException(ExceptionMassage exceptionMassage) {
        super(exceptionMassage.name());
    }

    public AtmException(String message) {
        super(message);
    }
}
