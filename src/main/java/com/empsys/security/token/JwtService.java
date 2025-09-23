package com.empsys.security.token;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // Use a strong, secure secret key (min 32 characters for HS256)
    private static final String SECRET = "YourSuperSecretKey12345678901234567890";
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    // Token validity period (1 hour)
    private static final long EXPIRATION_TIME_MS = 60 * 60 * 1000;

    /**
     * Generates a JWT token for the given username.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT token and returns its claims.
     */
    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SIGNING_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Checks if the token is expired.
     */
    public static boolean isTokenExpired(String token) {
        Date expiration = validateToken(token).getExpiration();
        return expiration.before(new Date());
    }

    /**
     * Extracts the username from a JWT token.
     */
    public static String extractUsername(String token) {
        return validateToken(token).getSubject();
    }
}
