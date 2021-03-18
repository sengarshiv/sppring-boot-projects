package com.advait.auth.model;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7460607493422423607L;

	public AuthenticationResponse() {

	}

	public AuthenticationResponse(String token, String expiresIn) {
		super();
		this.token = token;
		this.expiresIn = expiresIn;
	}

	private String token;

	private String expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [token=" + token + ", expiresIn=" + expiresIn + "]";
	}

}
