package com.egc.bankservice.controller;

import com.egc.bankservice.domain.DepositCashRequest;
import com.egc.bankservice.domain.WithdrawCashRequest;
import com.egc.bankservice.domain.receipt.Receipt;
import com.egc.bankservice.model.exception.BankServiceException;
import com.egc.bankservice.service.account.AccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Account Operations")
@RestController
@RequestMapping("api/account-operations")
public class AccountOperationsController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/balance/{cardNumber}")
    public BigDecimal checkBalance(@NotNull @Positive @PathVariable String cardNumber) throws BankServiceException {
        return accountService.checkBalance(cardNumber);
    }

    @PostMapping("deposit-cash")
    public Receipt depositCash(@Valid @RequestBody DepositCashRequest depositCashRequest) throws BankServiceException {
        String cardNumber = depositCashRequest.getCardNumber();
        BigDecimal amount = depositCashRequest.getAmount();

        return accountService.depositCash(cardNumber, amount);
    }

    @PostMapping("withdraw-cash")
    public Receipt withdrawCash(@Valid @RequestBody WithdrawCashRequest withdrawCashRequest) throws BankServiceException {
        String cardNumber = withdrawCashRequest.getCardNumber();
        BigDecimal amount = withdrawCashRequest.getAmount();

        return accountService.withdrawCash(cardNumber, amount);
    }
}

