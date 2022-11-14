package com.egc.atmservice.client;

import com.egc.bank.ApiClient;
import com.egc.bank.api.AccountOperationsApi;
import com.egc.bank.api.AuthenticationApi;
import com.egc.bank.api.CardValidationApi;
import org.springframework.stereotype.Service;

@Service
public class BankClientImpl implements BankClient {

    private final ApiClient client;

    private final AccountOperationsApi accountOperationsApi;
    private final AuthenticationApi authenticationApi;
    private final CardValidationApi cardValidationApi;

    public BankClientImpl () {
        client = new ApiClient();
        client.setBasePath("http://bank-service:8080");
        accountOperationsApi = new AccountOperationsApi(client);
        authenticationApi = new AuthenticationApi(client);
        cardValidationApi = new CardValidationApi(client);
    }

    @Override
    public AccountOperationsApi getAccountOperationsApi() {
        return accountOperationsApi;
    }

    @Override
    public AuthenticationApi getAuthenticationApi() {
        return authenticationApi;
    }

    @Override
    public CardValidationApi getCardValidationApi() {
        return cardValidationApi;
    }

    @Override
    public void addAccessTokenToRequestHeader(String accessToken) {
        client.addDefaultHeader("Authorization", String.format("Bearer %s", accessToken));
    }

    @Override
    public void removeAccessTokenFromRequestHeader() {
        client.addDefaultHeader("Authorization", null);
    }
}
