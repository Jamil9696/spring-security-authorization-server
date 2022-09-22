package com.ssAuthServer.authorizationserver.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomDefaultJwtToken implements OAuth2TokenCustomizer<JwtEncodingContext>  {

  private static final String ROLE_PREFIX = "ROLE_";



  @Override
  public void customize(JwtEncodingContext context) {


    Authentication principal = context.getPrincipal();



    if(Objects.equals(context.getTokenType().getValue(), "access_token") && principal instanceof JwtAuthenticationToken){
      context.
          getClaims()
          .claim("authorities", claimAuthoritiesToJwT(context.getPrincipal()) )
          .claim("roles", claimRolesToJWT(context.getPrincipal()));
    }
  }

  private Set<String> claimAuthoritiesToJwT(Authentication principal){
    return principal.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).filter(s -> s.startsWith(ROLE_PREFIX)).collect(Collectors.toSet());
  }

  private Set<String> claimRolesToJWT(Authentication principal){
    return principal.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority).filter(s -> !s.startsWith(ROLE_PREFIX)).collect(Collectors.toSet());
  }
}
