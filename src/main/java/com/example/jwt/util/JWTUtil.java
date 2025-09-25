package com.example.jwt.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.logging.Logger;

public class JWTUtil {
    private static final String SECRET_KEY = "mySecretKey";
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 phút
    private static final Logger LOGGER = Logger.getLogger(JWTUtil.class.getName());

    // Sinh token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Kiểm tra token
    public static String validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            LOGGER.severe("Token validation failed: " + e.getMessage());
            return null;
        }
    }
}