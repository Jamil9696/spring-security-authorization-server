package com.ssAuthServer.authorizationserver.security.providers;

import com.ssAuthServer.authorizationserver.security.jwt.CustomJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@RequiredArgsConstructor
@Slf4j
public class CustomJwtTokenProvider implements AuthenticationProvider {

 private final CustomJwtToken customJwtToken;
 private final UserDetailsService customUserDetailsService;
 private final PasswordEncoder passwordEncoder;


 @Override
 public Authentication authenticate(Authentication authentication){
  String email = authentication.getName();
  String password = authentication.getCredentials().toString();

  log.info("jwt provider starts login request");

  UserDetails user = customUserDetailsService.loadUserByUsername(email);

  return checkPassword(user, password);


 }

 @Override
 public boolean supports(Class<?> authentication) {
  return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
 }
 public String generateToken(String email){
  return customJwtToken.generateToken(email);
 }
 public String extractUsername(String token){
  return customJwtToken.getUserMailFromToken(token);
 }

 private Authentication checkPassword(UserDetails user, String rawPassword) throws BadCredentialsException {
  if (passwordEncoder.matches(rawPassword, user.getPassword())) {


   return new UsernamePasswordAuthenticationToken(
       user.getUsername(),
       user.getPassword(),
       user.getAuthorities());
  }

  throw new BadCredentialsException("Bad Credentials");
 }

 public boolean check(String jwt) {
  return customJwtToken.validateToken(jwt);
 }
}


