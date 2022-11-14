package com.egc.atmservice.client;

import com.egc.bank.api.AccountOperationsApi;
import com.egc.bank.api.AuthenticationApi;
import com.egc.bank.api.CardValidationApi;

public interface BankClient {

    AccountOperationsApi getAccountOperationsApi();

    AuthenticationApi getAuthenticationApi();

    CardValidationApi getCardValidationApi();

    void addAccessTokenToRequestHeader(String accessToken);

    void removeAccessTokenFromRequestHeader();
}
