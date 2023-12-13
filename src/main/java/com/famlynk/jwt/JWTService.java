package com.famlynk.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private final String JWT_SECRET;
	private final long JWT_EXPIRATION_TIME_IN_MILLISECONDS;

	@Autowired
	public JWTService(@Value("${spring.jwt.secret}") String jwtSecret,
			@Value("${spring.jwt.jwtExpirationInMs}") long jwtExpirationTimeInMs) {
		this.JWT_SECRET = jwtSecret;
		this.JWT_EXPIRATION_TIME_IN_MILLISECONDS = jwtExpirationTimeInMs;
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return tokenCreator(claims, userName);

	}

	public String tokenCreator(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION_TIME_IN_MILLISECONDS))
				.signWith(getSignedKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignedKey() {
		byte[] keyByte = Decoders.BASE64.decode(JWT_SECRET);
		return Keys.hmacShaKeyFor(keyByte);
	}

	public String extractUsernameFromToken(String theToken) {
		return extractClaim(theToken, Claims::getSubject);
	}

	public Date extractExpirationTimeFromToken(String theToken) {
		return extractClaim(theToken, Claims::getExpiration);
	}

	public Boolean validateToken(String theToken, UserDetails userDetails) {
		final String userName = extractUsernameFromToken(theToken);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(theToken));
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignedKey()).build().parseClaimsJws(token).getBody();

	}

	private boolean isTokenExpired(String theToken) {
		return extractExpirationTimeFromToken(theToken).before(new Date());
	}

}