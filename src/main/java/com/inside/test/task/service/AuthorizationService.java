package com.inside.test.task.service;

import com.inside.test.task.entity.UserInterface;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.sql.Date;
import java.time.LocalDate;

@Service
@Slf4j
public class AuthorizationService {

  @Value("${jwt.secret}")
  private String secret;

  public String generateToken(UserInterface user) {
    log.info("Generate token...");
    log.info("Secret :  " + secret);
    Date date = Date.valueOf(LocalDate.now().plusDays(1));
    Key key = getKeyFromSecret();
    return Jwts.builder()
        .claim("name: ", user.getUsername())
        .setExpiration(date)
        .signWith(key)
        .compact();
  }

  private Key getKeyFromSecret() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public boolean isTokenValid(final String username, final String token) {
    try {
      final Claims claims = getClaimsFromToken(token);
      final String usernameFromToken = claims.getSubject();

      return (username.equals(usernameFromToken))? true : false;
    } catch (ExpiredJwtException expEx) {
      log.error("Token expired");
    } catch (UnsupportedJwtException unsEx) {
      log.error("Unsupported jwt");
    } catch (MalformedJwtException mjEx) {
      log.error("Malformed jwt");
    } catch (Exception e) {
      log.error("Invalid token");
    }
    return false;
  }

  private Claims getClaimsFromToken(final String token) {
    return Jwts.parserBuilder().setSigningKey(getKeyFromSecret()).build().parseClaimsJws(token).getBody();
  }
}