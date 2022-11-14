package com.egc.atmservice.domain.mapper;

import com.egc.atmservice.domain.receipt.Receipt;
import com.egc.atmservice.domain.receipt.TransactionType;
import org.springframework.stereotype.Component;

@Component
public class ReceiptMapper {

    public Receipt mapReceipt(com.egc.bank.model.Receipt receipt) {
        Receipt atmReceipt = new Receipt();
        atmReceipt.setCardNumber(receipt.getCardNumber());
        atmReceipt.setBalance(receipt.getBalance());
        atmReceipt.setTransactionAmount(receipt.getTransactionAmount());
        TransactionType transactionType = getTransactionType(receipt);
        atmReceipt.setTransactionType(transactionType);
        return atmReceipt;
    }

    private static TransactionType getTransactionType(com.egc.bank.model.Receipt receipt) {
        TransactionType transactionType = null;
        switch (receipt.getTransactionType()) {
            case DEPOSIT:
                transactionType = TransactionType.DEPOSIT;
                break;
            case WITHDRAW:
                transactionType = TransactionType.DEPOSIT;
                break;
        }
        return transactionType;
    }
}
