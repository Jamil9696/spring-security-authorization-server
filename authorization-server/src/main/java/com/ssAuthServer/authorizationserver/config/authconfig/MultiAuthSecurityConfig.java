package com.ssAuthServer.authorizationserver.config.authconfig;


import com.ssAuthServer.authorizationserver.config.customizer.AuthMethodCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(AuthMethodCustomizer.MULTI_AUTH)
public class MultiAuthSecurityConfig {




}
