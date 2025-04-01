package com.waystech.authmanagement.security;

import com.waystech.authmanagement.Utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.xml.bind.DatatypeConverter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Data
public class JwtAuthProvider {
    @Value("${company.name}")
    private String companyName;

    @Value("${application.access-key}")
    private String accessKey;

    @Value("${jwt.access-expiration}")
    private int jwtAccessExpiration;

    @Value("${jwt.refresh-expiration}")
    private int jwtRefreshExpiration;
    private String generateSecret(){
        return DatatypeConverter.printBase64Binary(new byte[512/2]);
    }
    private Key generateSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(generateSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String, String> getClaims(){
        Map<String, String> claims = new HashMap<>();
        claims.put("name", companyName);
        claims.put("secret-key", accessKey);
        return claims;
    }


    public String generateAccessToken(Authentication authentication){
        String userEmail = authentication.getName();
        Map<String, String> claims = getClaims();
        claims.put("roles", authentication.getAuthorities().toString());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateUtils.getExpirationDate(jwtAccessExpiration))
                .signWith(generateSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }
    public String generateRefreshToken(Authentication authentication){
        String userEmail = authentication.getName();
        Map<String, String> claims = getClaims();
        claims.put("roles", authentication.getAuthorities().toString());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(DateUtils.getExpirationDate(jwtRefreshExpiration))
                .signWith(generateSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    private Claims extractClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(generateSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractSingleClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = extractClaims(token);
        return claimResolver.apply(claims);
    }
    private String extractCompanyName(String token){
        Claims claims = extractClaims(token);
        return (String) claims.get("name");
    }
    private String extractAccessKey(String token){
        Claims claims = extractClaims(token);
        return (String) claims.get("secret-key");
    }

    public String extractUserEmail(String token){
        return extractSingleClaim(token, Claims::getSubject);
    }
    private Date extractExpiration(String token){
        return extractSingleClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).after(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String userEmail = extractUserEmail(token);
        String name = extractCompanyName(token);
        String secretKey = extractAccessKey(token);
        return userEmail.equals(userDetails.getUsername())
                && isTokenExpired(token)
                && name.equals(companyName)
                && secretKey.equals(accessKey);
    }

}
