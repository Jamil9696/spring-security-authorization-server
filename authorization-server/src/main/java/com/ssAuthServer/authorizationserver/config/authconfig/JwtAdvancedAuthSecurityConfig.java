package com.ssAuthServer.authorizationserver.config.authconfig;


import com.ssAuthServer.authorizationserver.config.securityconfig.AuthMethodCustomizer;
import com.ssAuthServer.authorizationserver.security.filter.JwtAuthenticationFilter;
import com.ssAuthServer.authorizationserver.security.jwt.CustomJwtToken;
import com.ssAuthServer.authorizationserver.security.providers.CustomJwtTokenProvider;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@RequiredArgsConstructor
@Profile(AuthMethodCustomizer.JWT_ADVANCED_AUTH)
public class JwtAdvancedAuthSecurityConfig {


  private final PasswordEncoder byCryptPasswordEncoder;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
    auth.authenticationProvider(customAuthenticationProvider());

     http.formLogin()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated();

     http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

     return http.build();

  }
  @Bean
  public UserDetailsService userDetailsService(){
    return new CustomUserDetailsService();
  }

  @Bean
  public CustomJwtTokenProvider customAuthenticationProvider(){
    return new CustomJwtTokenProvider(customJwtToken(),userDetailsService(), byCryptPasswordEncoder );
  }

  @Bean
  public CustomJwtToken customJwtToken(){
    return new CustomJwtToken();
  }

  @Bean
  public OncePerRequestFilter jwtAuthenticationFilter(){
    return new JwtAuthenticationFilter(customAuthenticationProvider(), userDetailsService());
  }

}
