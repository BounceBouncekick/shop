package com.example.jwtjwt.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Slf4j
@Component
public class JWTUtil {
    private Environment environment;

    private Key key;


    public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
        log.info("secret :" + secret);
        byte[] byteSecretKey = secret.getBytes(StandardCharsets.UTF_8);
        log.info("byteSecretKey :" + byteSecretKey);
        key = Keys.hmacShaKeyFor(byteSecretKey);
        log.info("key : " + key);
    }

    public String getUsername(String token) {
        log.info("nametoken :" + token);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {
        log.info("Roletoken :" + token);
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String username, String role, Long expiredMs) {
        log.info("Stringrole : " + role );
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("role", role);
        log.info("claimrole :" + role);
        claims.setSubject(username); // Subject 값 설정

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (60000 * 30 * 24)))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}