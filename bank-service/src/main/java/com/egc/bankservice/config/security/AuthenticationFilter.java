package com.egc.bankservice.config.security;

import com.egc.bankservice.config.security.authenticationtoken.FingerPrintAuthenticationToken;
import com.egc.bankservice.config.security.authenticationtoken.PinCodeAuthenticationToken;
import com.egc.bankservice.config.security.loginattempt.LoginAttemptService;
import com.egc.bankservice.domain.auth.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.egc.bankservice.model.exception.ExceptionMassage.CARD_TEMPORARILY_BLOCKED;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthenticationFilter(String fitlterProcesessUr, AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl(fitlterProcesessUr);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            AuthenticationRequest authenticationRequest = objectMapper
                    .readValue(req.getInputStream(), AuthenticationRequest.class);
            if (loginAttemptService.isBlocked(authenticationRequest.getCardNumber())) {
                throw new LockedException(CARD_TEMPORARILY_BLOCKED.name());
            }
            Authentication authToken = generateAuthenticationToken(authenticationRequest);
            return getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Authentication generateAuthenticationToken(AuthenticationRequest authenticationRequest) {
        String cardNumber = authenticationRequest.getCardNumber();
        String credentials = authenticationRequest.getCredentials();
        Authentication authentication = null;
        switch (authenticationRequest.getAuthType()) {
            case PIN_CODE:
                authentication = new PinCodeAuthenticationToken(cardNumber, credentials);
                break;
            case FINGER_PRINT:
                authentication = new FingerPrintAuthenticationToken(cardNumber, credentials);
                break;
        }
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        String cardNumber = auth.getPrincipal().toString();

        String accessToken = jwtUtils.createAccessToken(cardNumber);
        loginAttemptService.loginSucceeded(cardNumber);

        String body = objectMapper.writeValueAsString(accessToken);

        log.info("Customer with card number " + cardNumber + " authenticated successfully");
        res.getWriter().write(body);
        res.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
        response.getWriter().flush();
    }
}