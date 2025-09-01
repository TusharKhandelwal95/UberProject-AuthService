package com.example.uberprojectauthservice.services;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private String SECRET;

    /**
     * This method creates a brand-new JWT token for us based on a payload
     * @return JWT token
     */
    private String createToken(Map<String, Object> payload, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);
        return Jwts.builder()
                .setClaims(payload)
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSignKey())
                .compact();
    }

    private Claims extractAllPayloads(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * This method checks if the token expiry was before the current time stamp or not ?
     * @param token JWT token
     * @return true if token is expired else false
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = extractEmail(token);
        return (userEmailFetchedFromToken.equals(email)) && !isTokenExpired(token);
    }

    private Object extractPayload(String token, String payloadKey) {
        Claims claim = extractAllPayloads(token);
        return claim.get(payloadKey);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("email", "a@b.com");
        mp.put("phoneNumber", "9999999999");
        String result = createToken(mp, "tush");
        System.out.println("Generated token is: " + result);
        System.out.println(extractPayload(result, "email").toString());
        System.out.println("Is token valid: " + validateToken(result, "tush"));
    }
}