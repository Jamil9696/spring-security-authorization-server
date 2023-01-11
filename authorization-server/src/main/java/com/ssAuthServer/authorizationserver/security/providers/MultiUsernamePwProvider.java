package com.ssAuthServer.authorizationserver.security.providers;

import com.ssAuthServer.authorizationserver.repository.OtpRepository;
import com.ssAuthServer.authorizationserver.security.authentications.UsernamePwAuthentication;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

// supports both http basic and form login.
@RequiredArgsConstructor
public class MultiUsernamePwProvider implements AuthenticationProvider  {


    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OtpGenerator otpGenerator;
    private final OtpRepository otpRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();


        UserDetails user = customUserDetailsService.loadUserByUsername(email);

        if(passwordEncoder.matches(password, user.getPassword())){

            otpRepository.saveNewOtp(
                otpGenerator.generateNewOtp(),
                LocalDateTime.now().toString(),
                customUserDetailsService.getId().get()
            );

            return generateToken(user);
        }

        throw new BadCredentialsException("Bad Credentials: username or password is wrong");

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePwAuthentication.class.isAssignableFrom(authentication);
    }

    private Authentication generateToken(UserDetails user) {

          return  new UsernamePasswordAuthenticationToken(
                  user.getUsername(),
                  user.getPassword(),
                  user.getAuthorities());
    }


}
