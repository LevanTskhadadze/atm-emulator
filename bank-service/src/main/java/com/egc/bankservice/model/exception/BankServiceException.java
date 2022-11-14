package com.egc.bankservice.model.exception;

import lombok.Getter;

@Getter
public class BankServiceException extends Exception {

    private ExceptionCode exceptionCode;

    public BankServiceException(ExceptionMassage exceptionMassage) {
        super(exceptionMassage.name());
    }

    public BankServiceException(ExceptionCode exceptionCode, ExceptionMassage exceptionMassage) {
        super(exceptionMassage.name());
        this.exceptionCode = exceptionCode;
    }
}
