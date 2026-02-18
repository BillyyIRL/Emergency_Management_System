package com.EMS.EMS.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours
    private final Key key;

    // Inject secret from application.yml
    public JwtService(@Value("${jwt.secret}") String secret) {
        // Convert secret string to proper key
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Generate token using userId and role
    public String generateToken(Long userId, Enum<?> role) {
        return Jwts.builder()
                .setSubject(userId.toString())       // store userId as subject
                .claim("role", role.name())          // store role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)                       // sign with Key object
                .compact();
    }

    // Extract userId from token
    public Long extractUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    // Extract role from token
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Validate token
    public boolean isTokenValid(String token) {
        try {
            getClaims(token); // will throw exception if invalid
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Private helper to parse claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
