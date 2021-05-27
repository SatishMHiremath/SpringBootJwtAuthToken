package com.example.springsecurityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityjwt.models.AuthenticationRequest;
import com.example.springsecurityjwt.models.AuthenticationResponse;
import com.example.springsecurityjwt.utils.JwtTokenUtils;

@RestController
public class ResourceController {

	@Autowired
	AuthenticationManager authManager;
	
	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtTokenUtils jwtTokenUtils;
	
	@RequestMapping("/res")
	public String getResource() {
		return "Hey Welcome to Spring Security";
	}

	@RequestMapping(value="/auth", method= RequestMethod.POST)
	public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authRequest) throws Exception {
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
					authRequest.getPassword()));
		} 
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect UserName or Password", e);
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		final String jwt = jwtTokenUtils.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));

	}
}
