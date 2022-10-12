package com.ssAuthServer.authorizationserver.config.securityconfig;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
public class CorsCustomizer {

  @Value("${spring.security.oauth2.allowed-origins")
  private String[] allowedOrigin;

  @Value("${spring.security.oauth2.allowed-headers")
  private String[] allowedHeader;

  @Value("${spring.security.oauth2.allowed-methods")
  private String[] allowedMethod;



  public void corsCustomizer(HttpSecurity http) throws Exception {
    http.cors(c -> {
      CorsConfigurationSource source = s -> {
        CorsConfiguration cc = new CorsConfiguration();
        cc.setAllowCredentials(true);
        cc.setAllowedOrigins(List.of(allowedOrigin));
        cc.setAllowedHeaders(List.of(allowedHeader));
        cc.setAllowedMethods(List.of(allowedMethod));
        return cc;
      };

      c.configurationSource(source);
    });
  }
}
