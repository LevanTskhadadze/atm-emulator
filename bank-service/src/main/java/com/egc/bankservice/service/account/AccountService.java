package com.egc.bankservice.service.account;


import com.egc.bankservice.domain.receipt.Receipt;
import com.egc.bankservice.model.exception.BankServiceException;

import java.math.BigDecimal;

public interface AccountService {

    BigDecimal checkBalance(String cardNumber) throws BankServiceException;

    Receipt depositCash(String cardNumber, BigDecimal amount) throws BankServiceException;

    Receipt withdrawCash(String cardNumber, BigDecimal amount) throws BankServiceException;
}
