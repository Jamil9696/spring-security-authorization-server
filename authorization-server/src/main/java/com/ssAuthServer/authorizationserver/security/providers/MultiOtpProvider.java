package com.ssAuthServer.authorizationserver.security.providers;

import com.ssAuthServer.authorizationserver.entities.Otp;
import com.ssAuthServer.authorizationserver.repository.OtpRepository;
import com.ssAuthServer.authorizationserver.security.authentications.UsernameOtpAuthentication;
import com.ssAuthServer.authorizationserver.security.userdetails.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
public class MultiOtpProvider implements AuthenticationProvider {

  private final CustomUserDetailsService customUserDetailsService;
  private final OtpRepository otpRepository;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String email = authentication.getName();
    String otp = authentication.getCredentials().toString();

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

     if(customUserDetailsService.getId().isEmpty()) {
       return new UsernameOtpAuthentication(userDetails.getUsername(),otp);
     }

    Optional<Otp> toCompareWith = otpRepository.findOtpByUserId(customUserDetailsService.getId().get());

     if(toCompareWith.isPresent() && toCompareWith.get().getOtp().equals(otp)){

       otpRepository.resetOtp(customUserDetailsService.getId().get());
       return new UsernameOtpAuthentication(userDetails.getUsername(), otp, Collections.emptyList());
     }

     throw new BadCredentialsException("Bad Credentials: otp is wrong");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernameOtpAuthentication.class.isAssignableFrom(authentication);
  }

}
