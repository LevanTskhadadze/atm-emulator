package com.egc.atmservice.service.opertaion;

import com.egc.atmservice.domain.exception.AtmException;
import com.egc.bank.model.Receipt;

import java.math.BigDecimal;

public interface AtmOperationService {

    BigDecimal checkBalance() throws AtmException;

    Receipt depositCash(BigDecimal amount) throws AtmException;

    Receipt withdrawCash(BigDecimal amount) throws AtmException;
}
