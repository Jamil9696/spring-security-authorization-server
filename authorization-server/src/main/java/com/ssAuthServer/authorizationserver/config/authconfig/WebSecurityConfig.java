package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.securityconfig.CorsCustomizer;
import com.ssAuthServer.authorizationserver.config.securityconfig.LdapCustomizer;
import com.ssAuthServer.authorizationserver.security.authenticationmanager.CustomAuthenticationManager;
import com.ssAuthServer.authorizationserver.security.providers.CustomAuthenticationProvider;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

  private final CorsCustomizer corsCustomizer;
  private final LdapCustomizer ldapCustomizer;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    corsCustomizer.corsCustomizer(http);
    ldapCustomizer.ldapConfigure(http, bCryptPasswordEncoder());

    return http.formLogin()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .build();
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(){
    return new CustomUserDetailsService();
  }

  @Bean
  public AuthenticationManager authenticationManager(){
    return new CustomAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider customAuthenticationProvider(){
    return new CustomAuthenticationProvider(userDetailsService(), bCryptPasswordEncoder() );
  }
}
