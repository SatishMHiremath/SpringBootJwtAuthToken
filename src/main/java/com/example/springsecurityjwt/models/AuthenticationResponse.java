package com.example.springsecurityjwt.models;

public class AuthenticationResponse {

	private final String jwtToken;

	public AuthenticationResponse(String jwt) {
		this.jwtToken = jwt;
	}

	public String getJwtToken() {
		return jwtToken;
	}

}
