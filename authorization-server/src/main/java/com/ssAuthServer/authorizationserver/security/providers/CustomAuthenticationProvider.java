package com.ssAuthServer.authorizationserver.security.providers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Set;


@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider  {

    private final UserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = customUserDetailsService.loadUserByUsername(email);
        return checkPassword(user, password);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Authentication checkPassword(UserDetails user, String rawPassword) {
        
        if (passwordEncoder.matches(rawPassword, user.getPassword())) {


            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());


            authenticationToken.setDetails(Map.of("backend1", Set.of("RoleA", "RoleB"), "backendB",Set.of("RoleC", "RoleD") ));
            return authenticationToken;
        }

        throw new BadCredentialsException("Bad Credentials");
    }
}
