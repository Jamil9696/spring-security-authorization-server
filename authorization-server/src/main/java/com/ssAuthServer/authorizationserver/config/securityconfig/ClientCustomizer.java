package com.ssAuthServer.authorizationserver.config.securityconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.UUID;

@Getter
@Configuration
public class ClientCustomizer {


  private final String redirectUrl;
  private final TokenSettings tokenSettings;
  private final PasswordEncoder passwordEncoder;

  public ClientCustomizer(
      @Autowired PasswordEncoder passwordEncoder,
      @Autowired TokenSettings tokenSettings,
      @Value("${spring.security.oauth2.redirect-url}")String redirectUrl) {
    this.passwordEncoder = passwordEncoder;
    this.redirectUrl = redirectUrl;
    this.tokenSettings = tokenSettings;
  }

  // template for adding more clients
  private RegisteredClient registeredClientTemplate(String clientID, String clientSecret){
    return RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId(clientID)
        .clientSecret(passwordEncoder.encode(clientSecret))
        .scope(OidcScopes.OPENID)
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        .authorizationGrantType(AuthorizationGrantType.PASSWORD)
        .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
        .redirectUri(redirectUrl)
        .tokenSettings(TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofHours(10))
            .refreshTokenTimeToLive(Duration.ofHours(10))
            .build())
        .clientSettings(ClientSettings.builder()
            .requireAuthorizationConsent(true)
            .build())
        .build();

  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){
    JdbcRegisteredClientRepository jdbc = new  JdbcRegisteredClientRepository(jdbcTemplate);
    return jdbc;
  }

}
