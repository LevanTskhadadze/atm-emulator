package com.egc.atmservice.session;


import com.egc.atmservice.domain.auth.AuthType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Session {

    String cardNumber;

    AuthType authType;

    boolean authenticated;

    String accessToken;

    public Session(String cardNumber, AuthType authType) {
        this.cardNumber = cardNumber;
        this.authType = authType;
        this.authenticated = false;
    }
}
