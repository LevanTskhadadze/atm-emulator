package com.egc.bankservice.model.exception.account;

import com.egc.bankservice.model.exception.BankServiceException;
import com.egc.bankservice.model.exception.ExceptionCode;

import static com.egc.bankservice.model.exception.ExceptionMassage.INSUFFICIENT_FUNDS;

public class AccountException {

    public static BankServiceException insufficientFunds() {
        return new BankServiceException(ExceptionCode.BAD_REQUEST, INSUFFICIENT_FUNDS);
    }
}
