package lk.ucsc.NovelGeek.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.ucsc.NovelGeek.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5*60*60*1000;
    public static final String SIGNING_KEY = "aweg34y54hw54h3h135h2455444442h5245h245h25h34gre3qh4qh34t";

    public static String generateToken(Users principal) {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_VALIDITY_SECONDS);
        return Jwts.builder()
                .claim("username", principal.getUsername())
                .claim("id", principal.getId())
                .claim("image", principal.getImageUrl())
                .setSubject(principal.getEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
            Key key = Keys.hmacShaKeyFor(keyBytes);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt);
            return true;
        } catch (SecurityException ex) {
            logger.error("Invalid JWT signature");
            throw new RuntimeException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            throw new RuntimeException("Invalid JWT token\"");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
            throw new RuntimeException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            throw new RuntimeException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            throw new RuntimeException("JWT claims string is empty.");
        }
    }

    public String getUserNameFromJwt(String jwt) {
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

        return claims.getSubject();
    }
}