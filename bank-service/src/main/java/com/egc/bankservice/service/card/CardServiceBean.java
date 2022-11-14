package com.egc.bankservice.service.card;

import com.egc.bankservice.model.entity.card.AuthType;
import com.egc.bankservice.model.entity.card.Card;
import com.egc.bankservice.model.exception.BankServiceException;
import com.egc.bankservice.model.exception.card.CardException;
import com.egc.bankservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceBean implements CardService {

    @Autowired
    private CardRepository cardRepository;

    public AuthType getCardAuthenticationType(String cardNumber) throws BankServiceException {
        Card card = cardRepository.findByNumberWithAuthentication(cardNumber)
                                  .orElseThrow(CardException::invalidCard);

        return card.getAuthenticationDetails().getAuthType();
    }
}
