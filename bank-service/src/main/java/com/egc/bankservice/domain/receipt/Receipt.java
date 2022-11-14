package com.egc.bankservice.domain.receipt;

import com.egc.bankservice.model.entity.transaction.TransactionType;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Receipt {

    String cardNumber;

    BigDecimal balance;

    BigDecimal transactionAmount;

    TransactionType transactionType;
}
