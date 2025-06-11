package com.example.projetoSpring.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        String roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException("Token inválido ou expirado.");
        }
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public String extractRoles(String token){
        return (String) getClaims(token).get("roles");
    }

    public boolean isTokenValid(String token) {
        Claims claims = getClaims(token);
        return claims.getExpiration().after(new Date());
    }
}
