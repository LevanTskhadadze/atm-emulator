package com.egc.bankservice.config.security.authenticationtoken;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class PinCodeAuthenticationToken extends AbstractAuthenticationToken {

	private final String principal;
	private final String credentials;

	public PinCodeAuthenticationToken(String principal, String credentials) {
		super(new ArrayList<>());
		this.principal = principal;
		this.credentials = credentials;
	}

	public PinCodeAuthenticationToken(String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.credentials = credentials;
		super.setAuthenticated(true);
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}
}
