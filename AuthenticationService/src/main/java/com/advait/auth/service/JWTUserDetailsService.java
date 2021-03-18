package com.advait.auth.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JWTUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.equals("admin")) {
			return new User("admin", "$2y$08$y47t5o4zM69o5RGS8SaSj.n1DLznSTWWdJgJfM6oFUmJlaWh8IASK",
					Collections.emptyList());
		} else {
			throw new UsernameNotFoundException("User not found : " + username);
		}
	}

}
