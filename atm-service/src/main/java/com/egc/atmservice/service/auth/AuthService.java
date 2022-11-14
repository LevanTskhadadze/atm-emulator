package com.egc.atmservice.service.auth;

import com.egc.atmservice.domain.exception.AtmException;

public interface AuthService {

    String validateCard(String cardNumber) throws AtmException;

    void enterPinCode(String pinCode) throws AtmException;

    void useFingerPrintScanner(String fingerPrint) throws AtmException;

    void exitAtm();
}
