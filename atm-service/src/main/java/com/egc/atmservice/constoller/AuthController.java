package com.egc.atmservice.constoller;


import com.egc.atmservice.domain.exception.AtmException;
import com.egc.atmservice.service.auth.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/authentication")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("inser-card/{cardNumber}")
    String insertCard(@NotBlank @PathVariable String cardNumber) throws AtmException {
        return authService.validateCard(cardNumber);
    }

    @PostMapping("enter-pin/{pinCode}")
    void enterPin(@NotBlank @PathVariable String pinCode) throws AtmException {
        authService.enterPinCode(pinCode);
    }

    @PostMapping("use-finger-print-scanner/{fingerPrint}")
    void useFingerPrintScanner(@NotBlank @PathVariable String fingerPrint) throws AtmException {
        authService.useFingerPrintScanner(fingerPrint);
    }

    @GetMapping("exit=atm")
    void exitAtm() throws AtmException {
        authService.exitAtm();
    }

}
