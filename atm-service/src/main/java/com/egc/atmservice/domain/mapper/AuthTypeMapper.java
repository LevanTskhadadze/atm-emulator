package com.egc.atmservice.domain.mapper;

import com.egc.atmservice.domain.auth.AuthType;
import com.egc.bank.model.AuthenticationRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthTypeMapper {

    public AuthType mapAuthType(AuthenticationRequest.AuthTypeEnum  authTypeEnum) {
        AuthType authType = null;
        switch (authTypeEnum) {
            case PIN_CODE:
                authType = AuthType.PIN_CODE;
                break;
            case FINGER_PRINT:
                authType = AuthType.FINGER_PRINT;
                break;
        }
        return authType;
    }

    public AuthenticationRequest.AuthTypeEnum mapAuthTypeEnum(AuthType  authType) {
        AuthenticationRequest.AuthTypeEnum authTypeEnum = null;
        switch (authType) {
            case PIN_CODE:
                authTypeEnum = AuthenticationRequest.AuthTypeEnum.PIN_CODE;
                break;
            case FINGER_PRINT:
                authTypeEnum = AuthenticationRequest.AuthTypeEnum.FINGER_PRINT;
                break;
        }
        return authTypeEnum;
    }
}
