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

  private final String client;
  private final String clientSecret;
  private final String redirectUrl;
  private final TokenSettings tokenSettings;

  public ClientCustomizer(
      @Autowired PasswordEncoder passwordEncoder,
      @Autowired TokenSettings tokenSettings,
      @Value("${spring.security.oauth2.resourceserver.client}")String client,
      @Value("${spring.security.oauth2.resourceserver.client-secret}")String clientSecret,
      @Value("${spring.security.oauth2.redirect-url}")String redirectUrl) {
    this.client = client;
    this.clientSecret = passwordEncoder.encode(clientSecret);
    this.redirectUrl = redirectUrl;
    this.tokenSettings = tokenSettings;
  }

  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate){

    /*var rc = RegisteredClient.withId(UUID.randomUUID().toString())
        .clientId("client")
        .clientSecret(clientSecret)
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
        .build();*/

    return new JdbcRegisteredClientRepository(jdbcTemplate);
    //registeredClientRepository.save(rc);


  }

}
