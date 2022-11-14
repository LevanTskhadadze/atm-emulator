package com.egc.bankservice.domain;

import lombok.Data;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class DepositCashRequest {

    @NotBlank
    @LuhnCheck
    private String cardNumber;

    @NotNull
    @Positive
    private BigDecimal amount;
}
