package com.ojasare.notes.security.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ojasare.notes.security.jwt.constant.JWTUtil.*;

@Component
@Getter
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final TokenBlacklist tokenBlacklist;

    public JwtUtils(RsaKeyProperties rsaKeys, TokenBlacklist tokenBlacklist) {
        this.privateKey = rsaKeys.privateKey();
        this.publicKey = rsaKeys.publicKey();
        this.tokenBlacklist = tokenBlacklist;
    }

    public String extractTokenFromHeaderIfExists(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public String generateAccessToken(String usernameOrEmail, List<String> roles) {
        return Jwts.builder()
                .subject(usernameOrEmail)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_ACCESS_TOKEN))
                .issuer(ISSUER)
                .claim("roles", roles)
                .signWith(privateKey)
                .compact();
    }

    public String generateRefreshToken(String usernameOrEmail) {
        return Jwts.builder()
                .issuer(ISSUER)
                .subject(usernameOrEmail)
                .expiration(new Date(System.currentTimeMillis() + EXPIRE_REFRESH_TOKEN))
                .signWith(privateKey)
                .compact();
    }

    public Map<String, String> getTokensMap(String jwtAccessToken, String jwtRefreshToken) {
        Map<String, String> idTokens = new HashMap<>();
        idTokens.put("access_token", jwtAccessToken);
        idTokens.put("refresh_token", jwtRefreshToken);
        return idTokens;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            if (tokenBlacklist.isBlacklisted(authToken)) {
                logger.error("JWT token is blacklisted");
                return false;
            }
            Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw e;
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}