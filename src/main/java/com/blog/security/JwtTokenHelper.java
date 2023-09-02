package com.blog.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {
	
	//public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	private String secret = "jwtTokenKey";
	
	//Retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFormToken(token, Claims::getSubject);
	}
	
	//Retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFormToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimFormToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims=getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	//For retrieveing any information from token we well need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	//Check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration=getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//Generate token for user
	@SuppressWarnings("unused")
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims=new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}


	//While createing the token-
	//1. Define claims of the token, like Issuer, Expiration, Subject, and the Id
	//2. Sign the JWT using the HS512 algorithm and secret key.
	//3. According the JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-)
	
	 private String doGenerateToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
	                .signWith(SignatureAlgorithm.HS512, secret).compact();
	    }

	//Validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
}











