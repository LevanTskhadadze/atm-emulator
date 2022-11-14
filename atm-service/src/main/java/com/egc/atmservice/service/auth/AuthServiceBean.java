package com.egc.atmservice.service.auth;

import com.egc.atmservice.client.BankClient;
import com.egc.atmservice.domain.auth.AuthType;
import com.egc.atmservice.domain.exception.AtmException;
import com.egc.atmservice.domain.exception.ExceptionMassage;
import com.egc.atmservice.domain.mapper.AuthTypeMapper;
import com.egc.atmservice.session.SessionManager;
import com.egc.atmservice.utils.ObjectMapperUtils;
import com.egc.bank.model.AuthenticationRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceBean implements AuthService {

    @Autowired
    private SessionManager sessionManager;

    @Autowired
    private AuthTypeMapper authTypeMapper;

    @Autowired
    private BankClient bankClient;

    @Override
    @CircuitBreaker(name = "AuthServiceBean")
    public String validateCard(String cardNumber) throws AtmException {
        String response = ObjectMapperUtils.deserialize(bankClient.getCardValidationApi().getCardAuthenticationType(cardNumber), String.class);
        AuthenticationRequest.AuthTypeEnum authTypeEnum = AuthenticationRequest.AuthTypeEnum.valueOf(response);
        AuthType authType = authTypeMapper.mapAuthType(authTypeEnum);
        sessionManager.createSession(cardNumber, authType);
        return getResponse(authType);
    }

    @Override
    @CircuitBreaker(name = "AuthServiceBean")
    public void enterPinCode(String pinCode) throws AtmException {
        validateEnterPinCodeRequest();
        authenticate(pinCode);
    }

    @Override
    @CircuitBreaker(name = "AuthServiceBean")
    public void useFingerPrintScanner(String fingerPrint) throws AtmException {
        validateFingerPrintRequest();
        authenticate(fingerPrint);
    }

    @Override
    public void exitAtm() {
        sessionManager.closeSession();
    }

    private void authenticate(String credentials) throws AtmException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setCardNumber(sessionManager.getCardNumber());
        authenticationRequest.setAuthType(authTypeMapper.mapAuthTypeEnum(sessionManager.getAutType()));
        authenticationRequest.setCredentials(credentials);
        String accessToken = ObjectMapperUtils.deserialize(bankClient.getAuthenticationApi().authenticate(authenticationRequest), String.class);
        sessionManager.storeAccessToken(accessToken);
    }

    private void validateEnterPinCodeRequest() throws AtmException {
        if (!sessionManager.sessionExists()) {
            throw new AtmException(ExceptionMassage.CARD_NOT_INSERTED);
        }
        if (sessionManager.getAutType() != AuthType.PIN_CODE) {
            throw new AtmException(ExceptionMassage.PIN_CODE_AUTHENTICATION_NOT_AVAILABLE);
        }
    }

    private void validateFingerPrintRequest() throws AtmException {
        if (!sessionManager.sessionExists()) {
            throw new AtmException(ExceptionMassage.CARD_NOT_INSERTED);
        }
        if (sessionManager.getAutType() != AuthType.FINGER_PRINT) {
            throw new AtmException(ExceptionMassage.FINGER_PRINT_AUTHENTICATION_NOT_AVAILABLE);
        }
    }

    private String getResponse(AuthType authType) {
        String response = null;
        switch (authType) {
            case PIN_CODE:
                response = "Please Insert Pin Code";
                break;
            case FINGER_PRINT:
                response = "Please useFingerPrintScanner";
                break;
        }
        return response;
    }
}
