package com.tutorial.crudmongoback.security.jwt;
import io.jsonwebtoken.*;

import com.tutorial.crudmongoback.security.service.UserPrincipal;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);
    @Value("${jwt.secret")
    private String secret;
    @Value("${jwt.expiration")
    private int expiration;

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .signWith(getKey(secret))
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .claim("roles", getRoles(userPrincipal))
                .claim("cara", "feísima")
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validationToken(String token)
    {
        try {
            Jwts.parserBuilder().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("fail expiration");
            ;
        } catch (UnsupportedJwtException e) {
            logger.error("fail Unsupported");
        } catch (MalformedJwtException e) {
            logger.error("fail Malformed");
        } catch (SignatureException e) {
            logger.error("fail Signature");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("fail token");
        }

        return false;
    }


    private List<String> getRoles(UserPrincipal principal){
        return  principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

    }
    private Key getKey(String secret){
        byte[] secretBytes= Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);


    }
}
