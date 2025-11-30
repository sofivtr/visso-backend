package cl.duoc.visso.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JWTProveedor {

    @Value("${jwt.secret:change-me-secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24h
    private long expirationMillis;

    private SecretKey getSigningKey() {
        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (Exception e) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMillis);
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(now)
                .expiration(exp)
                .signWith(getSigningKey())
                .compact();
    }

    public Claims validateAndGetClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

