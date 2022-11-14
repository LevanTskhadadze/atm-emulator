package com.egc.atmservice.session;

import com.egc.atmservice.domain.auth.AuthType;

public interface SessionManager {

    void createSession(String cardNumber, AuthType authType);

    void storeAccessToken(String accessToken);

    AuthType getAutType();

    boolean sessionExists();

    void closeSession();

    String getCardNumber();

    boolean isAuthenticated();
}
