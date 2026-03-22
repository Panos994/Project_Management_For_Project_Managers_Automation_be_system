package project_management_system.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${spring.private.token}")
    private String token;

    public String generateToken(UUID userId, String email){
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 day
                .signWith(Keys.hmacShaKeyFor(token.getBytes()))
                .compact();
    }
    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(token.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
