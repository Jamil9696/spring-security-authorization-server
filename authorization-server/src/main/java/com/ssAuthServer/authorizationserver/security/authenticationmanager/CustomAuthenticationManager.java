package com.ssAuthServer.authorizationserver.security.authenticationmanager;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationManager implements AuthenticationManager {


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    System.out.println("I'm in authenticationManager");
    return null;
  }
}
