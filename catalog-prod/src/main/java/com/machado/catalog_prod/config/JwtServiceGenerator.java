package com.machado.catalog_prod.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.machado.catalog_prod.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JwtServiceGenerator {  

  public String generateToken(User userDetails) {

      Map<String, Object> extraClaims = new HashMap<>();
      extraClaims.put("username", userDetails.getUsername());
      extraClaims.put("id", userDetails.getId().toString());
      extraClaims.put("role", userDetails.getRole());

      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(new Date().getTime() + 3600000 * JwtConfig.TOKEN_TIME))
              .signWith(JwtConfig.getSecureKey(), JwtConfig.SIGNATURE_ALGORITHM)
              .compact();
  }
  
  private Claims extractAllClaims(String token) {
      return Jwts
              .parserBuilder()
              .setSigningKey(JwtConfig.getSecureKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }


  public boolean isTokenValid(String token, UserDetails userDetails) {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
  }

  public String extractUsername(String token) {
      return extractClaim(token,Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
  }

}
