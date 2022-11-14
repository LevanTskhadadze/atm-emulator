package com.egc.bankservice.domain.auth;

import com.egc.bankservice.model.entity.card.AuthType;
import lombok.Data;

@Data
public class AuthenticationRequest {

    private String cardNumber;

    private String credentials;

    private AuthType authType;
}
