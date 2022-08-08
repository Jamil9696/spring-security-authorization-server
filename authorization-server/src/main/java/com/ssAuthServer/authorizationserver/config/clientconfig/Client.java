package com.ssAuthServer.authorizationserver.config.clientconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

@Getter
@Configuration
public class Client  {

  private final String client;
  private final String clientSecret;
  private final TokenSettings tokenSettings;

  public Client(
      @Autowired PasswordEncoder passwordEncoder,
      @Autowired TokenSettings tokenSettings,
      @Value("${spring.security.oauth2.resourceserver.client}")String client,
      @Value("${spring.security.oauth2.resourceserver.client-secret}")String clientSecret) {
    this.client = client;
    this.clientSecret = passwordEncoder.encode(clientSecret);
    this.tokenSettings = tokenSettings;
  }


  @Bean
  public RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
    // @formatter:off
    RegisteredClient registeredClient = RegisteredClient.withId("e4a295f7-0a5f-4cbc-bcd3-d870243d1b05")
        .clientId("huongdanjava1")
        .clientSecret("{noop}123")
        .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
        .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
        .tokenSettings(tokenSettings)
        .build();
    // @formatter:on

    JdbcRegisteredClientRepository registeredClientRepository =
        new JdbcRegisteredClientRepository(jdbcTemplate);
    registeredClientRepository.save(registeredClient);

    return registeredClientRepository;
  }

}
