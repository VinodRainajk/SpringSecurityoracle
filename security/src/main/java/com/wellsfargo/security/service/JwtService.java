package com.wellsfargo.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keybyte = Decoders.BASE64.decode(secrete_key);
        return Keys.hmacShaKeyFor(keybyte);
    }

    public String generateToken(UserDetails userDetails)
    {
        return generateToken(new HashMap<String, Object>(),userDetails);

    }

    public String generateToken(Map<String, Object> extraclaims, UserDetails userDetails)
    {
        return Jwts.builder().setClaims(extraclaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000+ 2400*60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenvalid(UserDetails userDetails, String token)
    {
        final String email = extractemail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenexpired(token));
    }

    private boolean isTokenexpired(String token) {
        return extractexpiration(token).before(new Date());
    }

    private Date extractexpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
