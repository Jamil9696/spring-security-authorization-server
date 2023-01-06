package com.ssAuthServer.authorizationserver.config.authconfig;

import com.ssAuthServer.authorizationserver.config.customizer.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.security.providers.OtpGenerator;
import com.ssAuthServer.authorizationserver.security.filter.MultiAuthenticationFilter;
import com.ssAuthServer.authorizationserver.security.jwt.TokenCustomizer;
import com.ssAuthServer.authorizationserver.security.manager.CustomAuthManager;
import com.ssAuthServer.authorizationserver.security.providers.MultiOtpProvider;
import com.ssAuthServer.authorizationserver.security.providers.MultiUsernamePwProvider;
import com.ssAuthServer.authorizationserver.security.providers.UsernamePasswordProvider;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;


@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.MULTI_AUTH)
public class MultiAuthConfig {

  private final PasswordEncoder passwordEncoder;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


    http.authenticationManager(customAuthenticationManager());
    http.addFilterAt(multiAuthenticationFilter(),BasicAuthenticationFilter.class);

    return http.formLogin()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .build();

  }

  @Bean
  public AuthenticationProvider customMultiUsernamePwProvider(){
    return new MultiUsernamePwProvider((CustomUserDetailsService) userDetailsService, passwordEncoder, otpGenerator());
  }

  @Bean
  public AuthenticationProvider customMultiOtpProvider(){
    return new MultiOtpProvider((CustomUserDetailsService) userDetailsService);
  }
  @Bean
  public AuthenticationProvider customUsernamePasswordProvider(){
    return new UsernamePasswordProvider(userDetailsService, passwordEncoder);
  }

  @Bean
  public AuthenticationManager customAuthenticationManager(){
    return new CustomAuthManager(List.of(customMultiUsernamePwProvider(), customMultiOtpProvider(), customUsernamePasswordProvider()));
  }

  @Bean
  public OtpGenerator otpGenerator(){
    return new OtpGenerator(passwordEncoder);
  }

  @Bean
  public OncePerRequestFilter multiAuthenticationFilter(){
    return new MultiAuthenticationFilter(customAuthenticationManager(), otpGenerator());
  }

  @Bean
  public TokenCustomizer oauth2JwtToken(){

    return new TokenCustomizer();
  }

}
