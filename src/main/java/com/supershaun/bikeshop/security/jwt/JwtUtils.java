package com.supershaun.bikeshop.security.jwt;

import com.supershaun.bikeshop.exceptions.TokenExpiredException;
import com.supershaun.bikeshop.exceptions.TokenRefreshException;
import com.supershaun.bikeshop.security.JwtUserDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${settings.jwt.secret}")
    private String jwtSecret;

    @Value("${settings.jwt.expiration}")
    private int jwtExpirationMs;

    public String generateJwtToken(JwtUserDetails userPrincipal) {
        return generateTokenFromUsername(
                userPrincipal.getUsername(),
                userPrincipal.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );
    }

    public String generateTokenFromUsername(String username, Collection<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);
        claims.put("sub", username);

        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getUserNameFromAuthorizationHeader(String header) {
        if (!header.startsWith("Bearer ")) {
            throw new TokenRefreshException(header, "Token is not valid");
        }

        return getUserNameFromJwtToken(header.substring(7));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return false;
    }
}
