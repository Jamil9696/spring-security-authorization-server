package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.securityconfig.CorsCustomizer;
import com.ssAuthServer.authorizationserver.security.providers.CustomAuthenticationProvider;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

  private final CorsCustomizer corsCustomizer;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    corsCustomizer.corsCustomizer(http);
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
  public AuthenticationProvider customAuthenticationProvider(){
    return new CustomAuthenticationProvider(userDetailsService(), bCryptPasswordEncoder() );
  }
}
