package com.egc.atmservice.constoller;

import com.egc.atmservice.domain.DepositCashRequest;
import com.egc.atmservice.domain.WithdrawCashRequest;
import com.egc.atmservice.domain.exception.AtmException;
import com.egc.atmservice.domain.mapper.ReceiptMapper;
import com.egc.atmservice.domain.receipt.Receipt;
import com.egc.atmservice.service.opertaion.AtmOperationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Tag(name = "ATM Operations")
@RestController
@RequestMapping("/api/atm-operations")
public class AtmOperationsController {

    @Autowired
    private AtmOperationService atmOperationService;

    @Autowired
    private ReceiptMapper receiptMapper;


    @GetMapping("/balance")
    BigDecimal checkBalance() throws AtmException {
        return atmOperationService.checkBalance();
    }

    @PostMapping("deposit-cash")
    Receipt depositCash(DepositCashRequest depositCashRequest) throws AtmException {
        BigDecimal amount = depositCashRequest.getAmount();
        return receiptMapper.mapReceipt(atmOperationService.depositCash(amount));
    }

    @PostMapping("withdraw-cash")
    Receipt withdrawCash(WithdrawCashRequest withdrawCashRequest) throws AtmException {
        BigDecimal amount = withdrawCashRequest.getAmount();

        return receiptMapper.mapReceipt(atmOperationService.withdrawCash(amount));
    }
}
