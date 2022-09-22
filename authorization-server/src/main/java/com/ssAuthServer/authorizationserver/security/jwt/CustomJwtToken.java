package com.ssAuthServer.authorizationserver.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
public class CustomJwtToken {

  @Value("${spring.security.oauth2.resourceserver.jwt.jwt-secret}")
  private String jwtSecret ;




  public String generateToken(String email){
    Instant now = Instant.now();
    Instant expireAt = now.plus(3, ChronoUnit.MINUTES);

    return Jwts.builder()
        .setSubject(email)
        .setIssuedAt(Date.from(now))
        .setExpiration(Date.from(expireAt))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserMailFromToken(String token){
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

    return claims.getSubject();
  }

  public boolean validateToken(String token){

    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    }catch (SignatureException signatureException){
      log.error("Invalid JWT Signature");
    }catch (MalformedJwtException malformedJwtException){
      log.error("Invalid JWT token");
    }catch (ExpiredJwtException expiredJwtException){
      log.error("Expired JWT token");
    }catch (UnsupportedJwtException unsupportedJwtException){
      log.error("Unsupported JWT token");
    }catch (IllegalArgumentException malformedJwtException){
      log.error("JWT claims string is empty ");
    }

    return false;
  }
}
