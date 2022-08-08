package com.ssAuthServer.authorizationserver.config.securityconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsCustomizer {


  @Value("${spring.security.oauth2.allowed-origin")
  private String[] allowedOrigin;

  public void corsCustomizer(HttpSecurity http) throws Exception {
    http.cors(c -> {
      CorsConfigurationSource source = s -> {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowCredentials(true);
        cc.setAllowedOrigins(List.of(allowedOrigin));
        cc.setAllowedHeaders(List.of("*"));
        cc.setAllowedMethods(List.of("*"));
        return cc;
      };

      c.configurationSource(source);
    });
  }
}
