package com.egc.atmservice.service.opertaion;

import com.egc.atmservice.client.BankClient;
import com.egc.atmservice.domain.exception.AtmException;
import com.egc.atmservice.session.SessionManager;
import com.egc.bank.model.DepositCashRequest;
import com.egc.bank.model.Receipt;
import com.egc.bank.model.WithdrawCashRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.egc.atmservice.domain.exception.ExceptionMassage.CARD_NOT_INSERTED;
import static com.egc.atmservice.domain.exception.ExceptionMassage.NOT_AUTHENTICATED;

@Service
public class AtmOperationServiceBean implements AtmOperationService {

    @Autowired
    private BankClient bankClient;

    @Autowired
    private SessionManager sessionManager;


    @Override
    @CircuitBreaker(name = "AtmOperationServiceBean")
    public BigDecimal checkBalance() throws AtmException {
        validateSession(sessionManager);

        String cardNumber = sessionManager.getCardNumber();

        return bankClient.getAccountOperationsApi().checkBalance(cardNumber);
    }

    @Override
    @CircuitBreaker(name = "AtmOperationServiceBean")
    public Receipt depositCash(BigDecimal amount) throws AtmException {
        validateSession(sessionManager);

        String cardNumber = sessionManager.getCardNumber();
        DepositCashRequest depositCashRequest = new DepositCashRequest();
        depositCashRequest.setCardNumber(cardNumber);
        depositCashRequest.setAmount(amount);

        return bankClient.getAccountOperationsApi().depositCash(depositCashRequest);
    }

    @Override
    @CircuitBreaker(name = "AtmOperationServiceBean")
    public Receipt withdrawCash(BigDecimal amount) throws AtmException {
        validateSession(sessionManager);

        String cardNumber = sessionManager.getCardNumber();
        WithdrawCashRequest withdrawCashRequest = new WithdrawCashRequest();
        withdrawCashRequest.setCardNumber(cardNumber);
        withdrawCashRequest.setAmount(amount);

        return bankClient.getAccountOperationsApi().withdrawCash(withdrawCashRequest);
    }

    private void validateSession(SessionManager sessionManager) throws AtmException {
        if (!sessionManager.sessionExists()) {
            throw new AtmException(CARD_NOT_INSERTED);
        }
        if (!sessionManager.isAuthenticated()) {
            throw new AtmException(NOT_AUTHENTICATED);
        }
    }
}
