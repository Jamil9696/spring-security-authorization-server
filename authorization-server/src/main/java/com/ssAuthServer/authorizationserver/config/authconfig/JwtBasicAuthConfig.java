package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.customizer.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.security.filter.JwtAuthenticationFilter;
import com.ssAuthServer.authorizationserver.security.jwt.TokenCustomizer;
import com.ssAuthServer.authorizationserver.security.providers.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;


@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.JWT_BASIC_AUTH)
public class JwtBasicAuthConfig {

  private final PasswordEncoder byCryptPasswordEncoder;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.authenticationProvider(customAuthenticationProvider());

    return http.formLogin().failureForwardUrl("/custom-login").and().build();
  }

  @Bean
  public AuthenticationProvider customAuthenticationProvider(){
    return new CustomAuthenticationProvider(userDetailsService, byCryptPasswordEncoder );
  }

  @Bean
  public OncePerRequestFilter jwtAuthenticationFilter(){
    return new JwtAuthenticationFilter(customAuthenticationProvider(), userDetailsService);
  }

  @Bean
  public TokenCustomizer oauth2JwtToken(){
    return new TokenCustomizer();
  }



}
