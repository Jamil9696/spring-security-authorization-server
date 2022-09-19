package com.ssAuthServer.authorizationserver.config.authconfig;


import com.ssAuthServer.authorizationserver.config.securityconfig.AuthMethodCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(AuthMethodCustomizer.MULTI_AUTH)
public class MultiAuthSecurityConfig {

  /*
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final UserDetailsService jwtUserDetailsService;
  private final JwtRequestFilter jwtRequestFilter;
  private final LdapUserAuthoritiesPopulator ldapUserAuthoritiesPopulator;

  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // Returns LdapAuthenticationProviderConfigurer to allow customization of the
    // LDAP authentication
    auth.ldapAuthentication()
        // Pass the LDAP patterns for finding the username.
        // The key "{0}" will be substituted with the username
        .userDnPatterns("uid={0},ou=users")
        // Pass search base as argument for group membership searches.
        .groupSearchBase("ou=groups")
        // Configures base LDAP path context source
        .contextSource().url("ldap://localhost:10389/dc=javachinna,dc=com")
        // DN of the user who will bind to the LDAP server to perform the search
        .managerDn("uid=admin,ou=system")
        // Password of the user who will bind to the LDAP server to perform the search
        .managerPassword("secret").and()
        // Configures LDAP compare operation of the user password to authenticate
        .passwordCompare().passwordEncoder(new LdapShaPasswordEncoder())
        // Specifies the attribute in the directory which contains the user password.
        // Defaults to "userPassword".
        .passwordAttribute("userPassword").and()
        // Populates the user roles by LDAP user name from database
        .ldapAuthoritiesPopulator(ldapUserAuthoritiesPopulator);
    // Basic / JWT authentication
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // Disable CSRF
    httpSecurity.csrf().disable()
        // Only admin can perform HTTP delete operation
        .authorizeRequests().antMatchers(HttpMethod.DELETE).hasRole(Role.ADMIN)
        // any authenticated user can perform all other operations
        .antMatchers("/products/**").hasAnyRole(Role.ADMIN, Role.USER).and().httpBasic()
        // Permit all other request without authentication
        .and().authorizeRequests().anyRequest().permitAll()
        // Reject every unauthenticated request and send error code 401.
        .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
        // We don't need sessions to be created.
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    // Add a filter to validate the tokens with every request
    httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
  }
  */


}
