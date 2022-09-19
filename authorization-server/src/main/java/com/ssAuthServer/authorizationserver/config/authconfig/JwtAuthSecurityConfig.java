package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.securityconfig.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.config.securityconfig.CorsCustomizer;
import com.ssAuthServer.authorizationserver.security.jwt.CustomJwtToken;
import com.ssAuthServer.authorizationserver.security.providers.CustomAuthenticationProvider;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
import com.ssAuthServer.authorizationserver.security.userdetails.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.JWT_AUTH)
public class JwtAuthSecurityConfig {

  private final PasswordEncoder byCryptPasswordEncoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

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
    return new CustomAuthenticationProvider(userDetailsService(), byCryptPasswordEncoder );
  }

  @Bean
  public SecurityUser securityUser(){
    return new SecurityUser();
  }

  @Bean
  public CustomJwtToken customJwtToken(){
    return new CustomJwtToken();
  }

  @Bean
  public ProviderSettings providerSettings(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUrl) {

    return ProviderSettings.builder().issuer(issuerUrl).build();
  }


}
