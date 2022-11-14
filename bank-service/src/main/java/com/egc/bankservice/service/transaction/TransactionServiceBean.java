package com.egc.bankservice.service.transaction;

import com.egc.bankservice.model.entity.account.Account;
import com.egc.bankservice.model.entity.transaction.Transaction;
import com.egc.bankservice.model.entity.transaction.TransactionType;
import com.egc.bankservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TransactionServiceBean implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction createTransaction(Account account, TransactionType transactionType, BigDecimal amount) {
        Transaction transaction = Transaction.builder()
                                             .account(account)
                                             .transactionType(transactionType)
                                             .amount(amount)
                                             .transactionTime(new Date())
                                             .build();

        transactionRepository.save(transaction);
        return transaction;
    }
}
