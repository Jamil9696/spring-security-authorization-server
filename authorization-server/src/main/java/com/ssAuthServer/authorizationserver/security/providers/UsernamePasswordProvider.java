package com.ssAuthServer.authorizationserver.security.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UsernamePasswordProvider implements AuthenticationProvider {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getName();
    String password = authentication.getCredentials().toString();


    UserDetails user = userDetailsService.loadUserByUsername(email);

    if(passwordEncoder.matches(password, user.getPassword())){
      return generateToken(user);
    }

    throw new BadCredentialsException("Bad Credentials: username or password is wrong");

  }

  private Authentication generateToken(UserDetails user) {

    return  new UsernamePasswordAuthenticationToken(
        user.getUsername(),
        user.getPassword(),
        user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
