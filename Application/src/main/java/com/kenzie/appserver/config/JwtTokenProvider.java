package com.kenzie.appserver.config;

import com.kenzie.appserver.repositories.model.UserRecord;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationInMs;
    private static final Logger LOGGER = Logger.getLogger(JwtTokenProvider.class.getName());

    public String generateToken(Authentication authentication) {
        UserRecord userPrincipal = (UserRecord) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUserId())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.severe("Signature exception encountered: " + ex.getMessage());
        } catch (MalformedJwtException ex) {
            LOGGER.severe("Malformed JWT exception encountered: " + ex.getMessage());
        } catch (ExpiredJwtException ex) {
            LOGGER.severe("Expired JWT exception encountered: " + ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            LOGGER.severe("Unsupported JWT exception encountered: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOGGER.severe("Illegal argument exception encountered: " + ex.getMessage());
        }
        return false;
    }
}


