package com.advait.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.advait.auth.service.JWTUserDetailsService;
import com.advait.auth.util.JwtTokenUtils;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	JwtTokenUtils jwtUtils;

	@Autowired
	JWTUserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String token = "";
		if(request.getRequestURL().toString().contains("authenticate")) {
			filterChain.doFilter(request, response);
			return;
		}
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			token = requestTokenHeader.substring(7);
			username = jwtUtils.getUsernameFromToken(token);
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtUtils.isValidToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken tokenAuthenticator=
						new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
				tokenAuthenticator.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(tokenAuthenticator);
			}

		}
		filterChain.doFilter(request, response);
	}

}
