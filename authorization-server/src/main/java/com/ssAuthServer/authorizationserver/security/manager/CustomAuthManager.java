package com.ssAuthServer.authorizationserver.security.manager;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.List;


@RequiredArgsConstructor
public class CustomAuthManager implements AuthenticationManager {

  private final List<AuthenticationProvider> providers;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    System.out.println("I was called :)");

    for (AuthenticationProvider provider: providers) {
      if(provider.supports(authentication.getClass())){
        return provider.authenticate(authentication);
      }
    }

    throw new ProviderNotFoundException("Provider not found");

  }
}
