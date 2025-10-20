package com.example.jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.logging.Logger;

public class JWTUtil {
    private static final String SECRET_KEY = "mySecretKeymySecretKeymySecretKey12"; // >=32 ký tự
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 phút
    private static final Logger LOGGER = Logger.getLogger(JWTUtil.class.getName());
    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            LOGGER.severe("Token validation failed: " + e.getMessage());
            return null;
        }
    }
}
