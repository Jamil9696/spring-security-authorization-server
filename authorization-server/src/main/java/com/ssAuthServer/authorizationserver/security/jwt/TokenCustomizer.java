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
  private static final String ROLE_SUFFIX = "__";



  @Override
  public void customize(JwtEncodingContext context) {


    Authentication principal = context.getPrincipal();

    if (Objects.equals(context.getTokenType().getValue(), "access_token") && principal instanceof UsernamePasswordAuthenticationToken) {

      Map<String, Set<String>> resourceAccess = claimLocalRolesToJwt(context.getPrincipal());

      if (!resourceAccess.isEmpty()) {
        context.
            getClaims()
            .claim("resource_access", resourceAccess);
      }

     Set<String> globalRoles = claimGlobalRolesToJWT(context.getPrincipal());

      if(!globalRoles.isEmpty()){
        context.
            getClaims()
            .claim("resource_access_global", claimGlobalRolesToJWT(context.getPrincipal()));
      }
    }
  }
  private Map<String, Set<String>> claimLocalRolesToJwt(Authentication principal){

    Map<String, Set<String>> map = new HashMap<>();

    principal.getAuthorities()
        .stream()
        .filter(a -> a.getAuthority().startsWith(ROLE_PREFIX) && a.getAuthority().contains(ROLE_SUFFIX))
        .forEach(s -> {
          if( map.containsKey(extractKey(s))){
            map.get(extractKey(s)).add(extractValues(s));
          }else{
            map.put(extractKey(s),newHashSet(extractValues(s)));
          }
        });

    return map;
  }

  private Set<String> claimGlobalRolesToJWT(Authentication principal){
    return principal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .filter( s -> s.startsWith(ROLE_PREFIX) && !s.contains(ROLE_SUFFIX))
        .collect(Collectors.toSet());
  }

  private String extractKey(GrantedAuthority g){
    String authority = g.getAuthority();

    if(authority.contains(ROLE_SUFFIX))
      authority = authority.substring(authority.lastIndexOf(ROLE_SUFFIX)).replace(ROLE_SUFFIX,"");

    return authority;
  }


  private String extractValues(GrantedAuthority g){
    String authority = g.getAuthority();

    if(authority.contains(ROLE_SUFFIX))
      authority = authority.substring(0, authority.lastIndexOf(ROLE_SUFFIX));

    return authority;
  }
}
