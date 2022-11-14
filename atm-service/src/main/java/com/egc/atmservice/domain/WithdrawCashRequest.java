package com.egc.atmservice.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class WithdrawCashRequest {

    @NotNull
    @Positive
    private BigDecimal amount;
}
