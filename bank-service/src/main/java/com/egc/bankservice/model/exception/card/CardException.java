package com.egc.bankservice.model.exception.card;

import com.egc.bankservice.model.exception.BankServiceException;
import com.egc.bankservice.model.exception.ExceptionCode;
import com.egc.bankservice.model.exception.ExceptionMassage;

public class CardException {

    public static BankServiceException invalidCard() {
        return new BankServiceException(ExceptionCode.BAD_REQUEST, ExceptionMassage.INVALID_CARD);
    }
}
