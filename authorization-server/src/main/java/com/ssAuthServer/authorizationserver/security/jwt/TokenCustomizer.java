package com.ssAuthServer.authorizationserver.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;


import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;


public class TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext>  {

  private static final String ROLE_PREFIX = "ROLE_";
  private static final String RESOURCE_PREFIX = "__";



  @Override
  public void customize(JwtEncodingContext context) {


    Authentication principal = context.getPrincipal();

    Map<String, Set<String>> scopedRoles = claimLocalRolesToJwt(context.getPrincipal());


    if(Objects.equals(context.getTokenType().getValue(), "access_token") && principal instanceof UsernamePasswordAuthenticationToken){
      context.
          getClaims()
          .claim("global_authorities", claimGlobalAuthoritiesToJwT(context.getPrincipal()) )
          .claim("global_permissions", claimGlobalRolesToJWT(context.getPrincipal()))
          .claim("resource_access", scopedRoles);
    }
  }

  private Map<String, Set<String>> claimLocalRolesToJwt(Authentication principal){

    Map<String, Set<String>> map = new HashMap<>();

    for (GrantedAuthority resource : principal.getAuthorities()){
      String authority = resource.getAuthority();
      if(authority.startsWith(ROLE_PREFIX) && authority.contains(RESOURCE_PREFIX)){
        String value = authority.substring(0, authority.lastIndexOf(RESOURCE_PREFIX));
        String key = authority.substring(authority.lastIndexOf(RESOURCE_PREFIX)).replace(RESOURCE_PREFIX,"");

        if(map.containsKey(key)){
          map.get(key).add(value);
        }else{
          map.put(key,newHashSet(value));
      }
    }
  }

    return map;
  }

  private Set<String> claimGlobalAuthoritiesToJwT(Authentication principal){
    return principal.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority).filter(
                s -> s.startsWith(ROLE_PREFIX) && !s.contains(RESOURCE_PREFIX))
        .collect(Collectors.toSet());
  }

  private Set<String> claimGlobalRolesToJWT(Authentication principal){
    return principal.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority).filter(
            s -> !s.startsWith(ROLE_PREFIX) && !s.contains(RESOURCE_PREFIX))
        .collect(Collectors.toSet());
  }
}
