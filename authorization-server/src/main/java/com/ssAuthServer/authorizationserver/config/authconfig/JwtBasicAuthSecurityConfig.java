package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.securityconfig.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.security.jwt.CustomDefaultJwtToken;
import com.ssAuthServer.authorizationserver.security.providers.CustomDefaultAuthenticationProvider;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
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


@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.JWT_BASIC_AUTH)
public class JwtBasicAuthSecurityConfig {

  private final PasswordEncoder byCryptPasswordEncoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.authenticationProvider(customAuthenticationProvider());

    return http.formLogin()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .build();
  }
  @Bean
  public UserDetailsService userDetailsService(){
    return new CustomUserDetailsService();
  }

  @Bean
  public AuthenticationProvider customAuthenticationProvider(){
    return new CustomDefaultAuthenticationProvider(userDetailsService(), byCryptPasswordEncoder );
  }

  @Bean
  public CustomDefaultJwtToken oauth2JwtToken(){
    return new CustomDefaultJwtToken();
  }



}
