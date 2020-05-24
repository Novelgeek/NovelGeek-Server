package lk.ucsc.NovelGeek.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ucsc.NovelGeek.model.Auth;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60;
    public static final String SIGNING_KEY = "aweg34y54hw54h3h135h2455444442h5245h245h25h34gre3qh4qh34t";

    public static String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwt) {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
        return true;
    }

    public String getUserNameFromJwt(String jwt) {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }
}