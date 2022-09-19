package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.securityconfig.ClientCustomizer;
import com.ssAuthServer.authorizationserver.config.securityconfig.CorsCustomizer;
import com.ssAuthServer.authorizationserver.security.jwt.CustomJwtToken;
import com.ssAuthServer.authorizationserver.security.userdetails.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class AuthorizationServerConfig {

  //http://localhost:9000/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized
  // http://localhost:9000/oauth2/authorize?response_type=code&client_id=client&scope=openid&redirect_uri=http://127.0.0.1:3000/authorized&code_challenge=hG1RD7JwvgNqo3ywr1Sbr8V8Br66Q3KeNjCxKyVMAaE&code_challenge_method=S256
  // http://localhost:9000/oauth2/token?client_id=client&redirect_uri=http://127.0.0.1:3000/authorized&grant_type=authorization_code&code=hG1RD7JwvgNqo3ywr1Sbr8V8Br66Q3KeNjCxKyVMAaE&code_verifier=eLIcOHHuDGY3sJoRi6Qvhfz0X_HSZUFOuX8tKgcVPzw


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


}
