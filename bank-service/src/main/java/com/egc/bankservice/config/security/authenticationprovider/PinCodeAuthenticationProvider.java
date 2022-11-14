package com.egc.bankservice.config.security.authenticationprovider;


import com.egc.bankservice.config.security.authenticationtoken.PinCodeAuthenticationToken;
import com.egc.bankservice.model.entity.card.AuthenticationDetails;
import com.egc.bankservice.model.entity.card.Card;
import com.egc.bankservice.model.exception.ExceptionMassage;
import com.egc.bankservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class PinCodeAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CardRepository cardRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String cardNumber = authentication.getPrincipal().toString();
		String pinCode = authentication.getCredentials().toString();
		Card card = cardRepository.findByNumberWithAuthentication(cardNumber)
								  .orElseThrow(() -> new UsernameNotFoundException(ExceptionMassage.INVALID_CARD.name()));
		AuthenticationDetails authenticationDetails = card.getAuthenticationDetails();

		if (passwordEncoder.matches(pinCode, authenticationDetails.getFingerPrint())) {
			return new PinCodeAuthenticationToken(cardNumber, pinCode, new ArrayList<>());
		} else {
			throw new BadCredentialsException(ExceptionMassage.INVALID_CREDENTIALS.name());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(PinCodeAuthenticationToken.class);
	}
}
