package com.rabobank.socle.authentication.entity;

import com.rabobank.socle.common.exception.TokenValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class AuthenticationToken {

    private final Jws<Claims> claims;

    public AuthenticationToken(String token, String accessTokenSecretKey) throws TokenValidationException {
        try {
            claims = Jwts.parser()
                .setSigningKey(accessTokenSecretKey)
                .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenValidationException("Expired or invalid JWT token");
        }
    }

    public boolean isExpired() {
        return claims.getBody().getExpiration().before(new Date());
    }

    public String getEmail() throws JwtException {
        return claims.getBody().getSubject();
    }
}
