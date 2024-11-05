package com.soni.SpringAuth.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    // 75C07591C1E2DE63BA29807D6C65FA445FE1B989AF91346FD266EC2B270F4F96     
    private static final String SECRET_KEY = "75C07591C1E2DE63BA29807D6C65FA445FE1B989AF91346FD266EC2B270F4F96";

    public SecretKey getSecretKey() {
        byte[] encodedByteArray = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(encodedByteArray);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public String generateToken(Map<String,Object> extractClaims,UserDetails userDetails){
        return Jwts.builder()
                .claims(extractClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
                .signWith(getSecretKey(), Jwts.SIG.HS256)
                .compact(); 
    }

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
            .parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public boolean isTokenValid(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date()); 
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims :: getExpiration);    
    }

}
