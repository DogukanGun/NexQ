package com.dag.nexq_userservice.security;

import com.dag.nexq_userservice.data.sec.UserDetailsImpl;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.util.Date;

@Component
public class TokenGenerator {

    @Value("${nexq.jwt.security.app.key}")
    private String APP_KEY;

    @Value("${nexq.jwt.security.expire.time}")
    private Long EXPIRE_TIME;

    public String generateJwtToken(UserDetailsImpl jwtUserDetails){

        Date expireDate = new Date(new Date().getTime() + EXPIRE_TIME);

        return Jwts.builder()
                .setId(jwtUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_KEY)
                .compact();
    }

    public String  findUserIdByToken(String token){
        Jws<Claims> claimsJws = parseToken(token);
        return claimsJws
                .getBody()
                .getId();
    }

    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(APP_KEY)
                .parseClaimsJws(token);
    }

    public boolean validateToken(String token){
        boolean isValid;
        try {
            Jws<Claims> claimsJws = parseToken(token);
            isValid = !isTokenExpired(claimsJws);
        } catch (Exception e){
            isValid = false;
        }

        return isValid;
    }

    private boolean isTokenExpired(Jws<Claims> claimsJws) {
        Date expirationDate = claimsJws.getBody().getExpiration();
        return expirationDate.before(new Date());
    }
}
