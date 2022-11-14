package com.egc.bankservice.service.card;

import com.egc.bankservice.model.entity.card.AuthType;
import com.egc.bankservice.model.exception.BankServiceException;

public interface CardService {

    AuthType getCardAuthenticationType(String cardNumber) throws BankServiceException;
}
