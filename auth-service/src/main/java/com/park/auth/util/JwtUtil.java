package com.park.auth.util;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
	public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {

		List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority) // ROLE_ADMIN, ROLE_USER
				.toList();

		return Jwts.builder().setSubject(username).claim("role", roles) // ✅ List<String>
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(getSigningKey())
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        try {
        	System.out.println("token ->"+token);
        	System.out.println("extractUsername(token) ->"+extractUsername(token));
        	System.out.println("isTokenExpired ->"+isTokenExpired(token));
            return (extractUsername(token).equals(username) && !isTokenExpired(token));
        } catch (ExpiredJwtException e) {
            return false;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            return false;
        }
    }
}