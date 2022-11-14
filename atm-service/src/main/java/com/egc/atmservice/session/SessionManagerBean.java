package com.egc.atmservice.session;

import com.egc.atmservice.client.BankClient;
import com.egc.atmservice.domain.auth.AuthType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionManagerBean implements SessionManager {

    private Session session;

    @Autowired
    private BankClient bankClient;

    @Override
    public void createSession(String cardNumber, AuthType authType) {
        session = new Session(cardNumber, authType);
    }

    @Override
    public void storeAccessToken(String accessToken) {
        bankClient.addAccessTokenToRequestHeader(accessToken);
        session.setAccessToken(accessToken);
        session.setAuthenticated(true);
    }

    @Override
    public AuthType getAutType() {
        return session.getAuthType();
    }

    @Override
    public boolean sessionExists() {
        return session != null;
    }

    @Override
    public void closeSession() {
        session = null;
        bankClient.removeAccessTokenFromRequestHeader();
    }

    @Override
    public String getCardNumber()  {
        return session.getCardNumber();
    }

    @Override
    public boolean isAuthenticated() {
        return session.isAuthenticated();
    }
}
