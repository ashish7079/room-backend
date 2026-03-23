package com.example.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

	private static final String Secret = "AshishkumarjhaRoseFlowe456789!@#$%^&*";
	
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(Secret.getBytes());
	}
	
	public String generateToken(String userName, String role) {

	    Map<String,Object> claims = new HashMap<>();

	    claims.put("role", role); 
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(getSigningKey(),SignatureAlgorithm.HS256)
				.compact();		
	}
	
	public String ExtractUserName(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	public boolean validateToken(String userName,String token) {
		String ExtractedUserName = (ExtractUserName(token));
		return (userName.equals(ExtractedUserName) && !TokenExpired(token));
	}

	private boolean TokenExpired(String token) {
		Date Expiration = Jwts.parserBuilder()
		.setSigningKey(getSigningKey())
		.build()
		.parseClaimsJws(token)
		.getBody()
		.getExpiration();
		
		return Expiration.before(new Date());
	}
	
}
