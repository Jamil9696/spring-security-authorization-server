package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.customizer.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.security.jwt.TokenCustomizer;
import com.ssAuthServer.authorizationserver.security.manager.CustomAuthManager;
import com.ssAuthServer.authorizationserver.security.providers.UsernamePasswordProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.MULTI_AUTH)
public class MultiAuthConfig {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


    http.authenticationManager(customAuthenticationManager());
    //auth.authenticationProvider(customAuthenticationProvider());

    return http.formLogin()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .build();

  }



  @Bean
  public AuthenticationProvider customUsernamePasswordProvider(){
    return new UsernamePasswordProvider(userDetailsService,passwordEncoder);
  }

  @Bean
  public AuthenticationManager customAuthenticationManager(){

    return new CustomAuthManager(List.of(customUsernamePasswordProvider()));
  }


  /*@Bean
  public OncePerRequestFilter jwtAuthenticationFilter(){
    return new JwtAuthenticationFilter(customAuthenticationProvider(), userDetailsService);
  }*/

  @Bean
  public TokenCustomizer oauth2JwtToken(){
    return new TokenCustomizer();
  }



}
