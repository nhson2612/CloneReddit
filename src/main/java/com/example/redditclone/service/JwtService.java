package com.example.redditclone.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    private static  final String signingKey = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public Claims getAllClaim(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    public String generateJwtToken(UserDetails userDetails){
        return  Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*24) )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getSigningKey(){
        byte[] bytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    public Date getExp(String jwt){
        return getAllClaim(jwt).getExpiration();
    }

    public boolean isTokenExpired(String jwt){
        return this.getExp(jwt).before(new Date());
    }

    public boolean validJwtToken(String jwt, UserDetails userDetails){
        return !this.isTokenExpired(jwt)  && (this.getUsername(jwt).equals(userDetails.getUsername())) ;
    }
    public String getUsername(String jwt){
        return this.getAllClaim(jwt).getSubject();
    }
}
