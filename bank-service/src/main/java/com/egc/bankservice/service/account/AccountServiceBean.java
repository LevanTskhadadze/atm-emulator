package com.egc.bankservice.service.account;

import com.egc.bankservice.domain.receipt.Receipt;
import com.egc.bankservice.model.entity.account.Account;
import com.egc.bankservice.model.entity.card.Card;
import com.egc.bankservice.model.entity.transaction.TransactionType;
import com.egc.bankservice.model.exception.BankServiceException;
import com.egc.bankservice.model.exception.account.AccountException;
import com.egc.bankservice.model.exception.card.CardException;
import com.egc.bankservice.repository.CardRepository;
import com.egc.bankservice.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceBean implements AccountService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionService transactionService;


    @Override
    @Transactional
    public BigDecimal checkBalance(String cardNumber) throws BankServiceException {
        Card card = cardRepository.findByNumberWithAccount(cardNumber)
                                  .orElseThrow(CardException::invalidCard);
        Account account = card.getAccount();
        return account.getBalance();
    }

    @Override
    @Transactional
    public Receipt depositCash(String cardNumber, BigDecimal amount) throws BankServiceException {
        Card card = cardRepository.findByNumberWithAccount(cardNumber)
                                  .orElseThrow(CardException::invalidCard);
        Account account = card.getAccount();

        account.setBalance(account.getBalance().add(amount));
        transactionService.createTransaction(account, TransactionType.DEPOSIT, amount);
        return new Receipt(card.getNumber(), account.getBalance(), amount, TransactionType.DEPOSIT);
    }

    @Override
    @Transactional
    public Receipt withdrawCash(String cardNumber, BigDecimal amount) throws BankServiceException {
        Card card = cardRepository.findByNumberWithAccount(cardNumber)
                                  .orElseThrow(CardException::invalidCard);
        Account account = card.getAccount();

        validateWithdrawRequest(account, amount);

        account.setBalance(account.getBalance().subtract(amount));
        transactionService.createTransaction(account, TransactionType.WITHDRAW, amount);
        return new Receipt(card.getNumber(), account.getBalance(), amount, TransactionType.WITHDRAW);
    }

    private void validateWithdrawRequest(Account account, BigDecimal amount) throws BankServiceException {
        if (account.getBalance().compareTo(amount) < 0) {
            throw AccountException.insufficientFunds();
        }
    }
}
