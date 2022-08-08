package com.ssAuthServer.authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;


@Configuration
public class JwksKeys{


  @Bean
  public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
    return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
  }

  @Bean
  public JWKSource<SecurityContext> jwkSource() {
    RSAKey rsaKey = generateRSAKey();
    JWKSet set = new JWKSet(rsaKey);
    return (j, sc) -> j.select(set);
  }

  @Bean
  public TokenSettings tokenSettings() {
    //@formatter:off
    return TokenSettings.builder()
        .accessTokenTimeToLive(Duration.ofMinutes(30L))
        .build();
    // @formatter:on
  }

  private RSAKey generateRSAKey() {
    try {
      KeyPairGenerator g = KeyPairGenerator.getInstance("RSA");
      g.initialize(2048);
      var keyPair = g.generateKeyPair();

      RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
      RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();

      return new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).keyID(UUID.randomUUID().toString()).build();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("Problem generating the keys");
    }
  }
}
