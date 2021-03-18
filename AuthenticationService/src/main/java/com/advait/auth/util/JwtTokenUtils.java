package com.advait.auth.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class JwtTokenUtils implements Serializable {

	/**
	 * Author: p@r@d0x
	 */
	private static final long serialVersionUID = 2139838391990971023L;

	@Value("${jwt.secret}")
	private String secret;

	private final long DEFAULT_JWT_TOKEN_VALIDITY = 5 * 60 * 60;

	public long tokenValidity;

	public JwtTokenUtils() {
		this.tokenValidity = DEFAULT_JWT_TOKEN_VALIDITY;
	}

	public JwtTokenUtils(long tokenValidity) {
		this.tokenValidity = tokenValidity;
	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims cl = getAllClaimsFromToken(token);
		return claimsResolver.apply(cl);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	public boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration.before(expiration);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + this.tokenValidity * 10000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	public boolean isValidToken(String token, UserDetails userDetails) {
		return (getUsernameFromToken(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
