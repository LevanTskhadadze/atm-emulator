package com.egc.bankservice.service.transaction;

import com.egc.bankservice.model.entity.account.Account;
import com.egc.bankservice.model.entity.transaction.Transaction;
import com.egc.bankservice.model.entity.transaction.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {

    Transaction createTransaction(Account account, TransactionType withdraw, BigDecimal amount);
}
