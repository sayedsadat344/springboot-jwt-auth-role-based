package com.sdattech.springjwt.jwt.service;

import com.sdattech.springjwt.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

//    FOR KEY GENERATION EASILY PASTE AND ENTER THE COMMD ON TERMINAL
//    node -e "console.log(require('crypto').randomBytes(32).toString('hex'))"

    private final String SECRET_KEY ="3089eeee5c2a9bfe7c0244765670c25d14c2805f4abfbf5dc692af8f3e78790a";


    public String generateToken(User user){
        return Jwts.builder().subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
                .signWith(getSecretKey())
                .compact();
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes =  Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver){
            Claims cliams =  extractAllClaims(token);
            return resolver.apply(cliams);

    }

    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){

        String username = extractUsername(token);
        return username.equals(user.getUsername()) && !isExpired(token);
    }


    public boolean isExpired(String token){
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token,Claims::getExpiration);
    }
}
