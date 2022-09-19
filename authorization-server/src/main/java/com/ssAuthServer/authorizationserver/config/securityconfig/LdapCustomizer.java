package com.ssAuthServer.authorizationserver.config.securityconfig;

import com.ssAuthServer.authorizationserver.repository.ResourceUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class LdapCustomizer {


  private final ResourceUserRepo resourceUserRepo;

  public void ldapConfigure(HttpSecurity http, PasswordEncoder passwordEncoder ) throws Exception {
    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);

    auth.ldapAuthentication()
        .userDnPatterns("uid={0},ou=people")
        .groupSearchBase("ou=groups")
        .contextSource()
        .url("ldap://localhost:8389/dc=springframework,dc=org")
        .and()
        .passwordCompare()
        .passwordEncoder(passwordEncoder)
        .passwordAttribute("userPassword");
       // .and()
        //.ldapAuthoritiesPopulator(ldapAuthoritiesPopulator());
  }



}
