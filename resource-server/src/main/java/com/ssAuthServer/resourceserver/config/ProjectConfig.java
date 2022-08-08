package com.ssAuthServer.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ProjectConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

   return httpSecurity.oauth2ResourceServer(
        j -> j.jwt().jwkSetUri("http://localhost:9000/oauth2/jwks")
    ).authorizeRequests()
       .anyRequest()
       .authenticated()
       .and()
       .build();

  }
}
