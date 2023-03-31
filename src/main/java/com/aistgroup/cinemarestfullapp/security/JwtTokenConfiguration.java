package com.aistgroup.cinemarestfullapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class JwtTokenConfiguration {

  private final String createTokenKey;
  private final String refreshSalt;
  private final long createTokenExpiration;
  private final long refreshTokenExpiration;

  public JwtTokenConfiguration(
      @Value("${token.create.secret-key}") String createTokenKey,
      @Value("${token.refresh.salt}") String refreshSalt,
      @Value("${token.create.expires-days}") long createTokenExpiration,
      @Value("${token.refresh.expires-days}") long refreshTokenExpiration) {
    this.createTokenKey = createTokenKey;
    this.refreshSalt = refreshSalt;
    this.createTokenExpiration = createTokenExpiration * 24 * 60 * 60 * 1000;
    this.refreshTokenExpiration = refreshTokenExpiration * 24 * 60 * 60 * 1000;
  }

  public String extractUsername(String token) {
    log.info("Extracting username from token...");
    return extractClaim(token, Claims::getSubject);
  }

  public String createToken(String username) {
    return generateToken(username, createTokenExpiration);
  }

  public String refreshToken(String username) {
    return generateToken(refreshSalt + username, refreshTokenExpiration);
  }

  public String generateToken(String username, Long expiration) {
    log.info("Generating token for user: {}", username);
    log.info("Generating token for user with expiration date: {}", expiration);
    return Jwts
        .builder()
        .setClaims(new HashMap<>())
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSignInKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  public boolean isTokenValid(String token, String userName) {
    String extractUsername = extractUsername(token);
    log.info("isTokenValid for user: {}", extractUsername);
    return (extractUsername.equals(userName)) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    log.info("Extracting expiration...");
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignInKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  private Key getSignInKey() {
    byte[] keyBytes = Decoders.BASE64.decode(createTokenKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }


}
