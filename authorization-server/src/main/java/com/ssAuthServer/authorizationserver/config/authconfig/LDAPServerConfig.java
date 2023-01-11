package com.ssAuthServer.authorizationserver.config.authconfig;


import com.ssAuthServer.authorizationserver.config.customizer.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.config.customizer.LdapCustomizer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.ACTIVE_DIRECTORY_AUTH)
public class LDAPServerConfig {

  private final PasswordEncoder byCryptPasswordEncoder;
  private final LdapCustomizer ldapCustomizer;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    ldapCustomizer.ldapConfigure(http, byCryptPasswordEncoder);

    return http.formLogin()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .build();
  }

}
