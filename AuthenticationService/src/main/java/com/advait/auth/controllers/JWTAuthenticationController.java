package com.advait.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.advait.auth.model.AuthenticationRequest;
import com.advait.auth.model.AuthenticationResponse;
import com.advait.auth.service.JWTUserDetailsService;
import com.advait.auth.util.JwtTokenUtils;

@RestController
public class JWTAuthenticationController {
	
	@Autowired
	JWTUserDetailsService userDetailsService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtTokenUtils jwtUtil;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticator(@RequestBody AuthenticationRequest request) throws Exception {
		authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails=userDetailsService.loadUserByUsername(request.getUsername());
		String token=jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(token,""+ jwtUtil.tokenValidity));
	}
	
	public void authenticate(String username,String password) throws Exception{
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

}
