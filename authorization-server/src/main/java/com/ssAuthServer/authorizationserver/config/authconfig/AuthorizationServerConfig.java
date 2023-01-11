package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.customizer.CorsCustomizer;
import com.ssAuthServer.authorizationserver.repository.ResourceUserRepo;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
import com.ssAuthServer.authorizationserver.security.userdetails.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class AuthorizationServerConfig {

  // http://localhost:9000/.well-known/openid-configuration
  // http://localhost:9000/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized
  // http://localhost:9000/oauth2/authorize?response_type=code&client_id=client3&scope=phone&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=A3vklrxtU-aRtbcEtbRgHOXfU0sELL5woybDyqRG370&code_challenge_method=S256
  // http://localhost:9000/oauth2/token?redirect_uri=http://127.0.0.1:3000/authorized&grant_type=authorization_code&code=7idqelzPdju-tc6Ox95Zdi2k9ls0CkCD27xyo_B7gD8Lxjl2CAvkMV-nqN5ob74ko8o63FTsVcrgu7Fz8rNcQAa1j-4m2IsapMudGBridSxunXbwoAurH1xEket9757n&code_verifier=G5lKjm3pg5ex8oGG9v5OsMBaPgEWLq534OpGlePBtRQ
  // code_challenge=A3vklrxtU-aRtbcEtbRgHOXfU0sELL5woybDyqRG370&code_challenge_method=S256
  private final CorsCustomizer corsCustomizer;

  @Bean
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public SecurityFilterChain securityASFilterChain(HttpSecurity http) throws Exception {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
    corsCustomizer.corsCustomizer(http);
    return http.formLogin().and().build();
  }

  @Bean
  public PasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ProviderSettings providerSettings(@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUrl) {
    return ProviderSettings.builder().issuer(issuerUrl).build();
  }

  @Bean
  public UserDetailsService userDetailsService(ResourceUserRepo resourceUserRepo, SecurityUser securityUser){
    return new CustomUserDetailsService(resourceUserRepo,securityUser);
  }


}
