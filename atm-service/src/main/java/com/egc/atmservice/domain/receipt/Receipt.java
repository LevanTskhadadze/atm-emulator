package com.egc.atmservice.domain.receipt;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Receipt {

    private String cardNumber;

    private BigDecimal balance;

    private BigDecimal transactionAmount;

    private TransactionType transactionType;
}

