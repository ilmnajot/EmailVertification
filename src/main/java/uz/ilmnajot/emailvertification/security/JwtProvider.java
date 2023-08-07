package uz.ilmnajot.emailvertification.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {
    private final String secretKey = "SecretKeyAndWords";
    private final long expireTime = 1000 * 24 * 24 * 3600L;



    public String generateToken(String email){
       return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
    public String getUsernameFromToken(String email){
        return Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(email)
                .getBody()
                .getSubject();

    }
}
