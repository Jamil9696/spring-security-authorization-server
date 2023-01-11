package com.ssAuthServer.authorizationserver.config.customizer;


import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthMethodCustomizer {


  public static final String NO_AUTH = "noauth";
  public static final String BASIC_AUTH = "basicauth";
  public static final String JWT_BASIC_AUTH = "jwtbasicauth";
  public static final String ACTIVE_DIRECTORY_AUTH = "adauth";
  public static final String MULTI_AUTH = "multiauth";
  public static final String OAUTH2 = "oauth2" ;
}
