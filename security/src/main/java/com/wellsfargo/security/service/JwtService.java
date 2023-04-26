package com.wellsfargo.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String secrete_key = "357538782F413F4428472B4B6250655368566D59713374367639792442264529";
    public String extractemail(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }


    public <T> T extractClaim(String Token , Function<Claims, T> claimresolver)
    {
        final Claims claims = extractAllClaims(Token);
        return  claimresolver.apply(claims);
    }

    private Claims extractAllClaims(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keybyte = Decoders.BASE64.decode(secrete_key);
        return Keys.hmacShaKeyFor(keybyte);
    }
}
