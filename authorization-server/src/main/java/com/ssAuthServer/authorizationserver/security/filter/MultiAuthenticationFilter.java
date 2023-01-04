package com.ssAuthServer.authorizationserver.security.filter;

import com.ssAuthServer.authorizationserver.security.providers.OtpGenerator;
import com.ssAuthServer.authorizationserver.security.authentications.UsernameOtpAuthentication;
import com.ssAuthServer.authorizationserver.security.authentications.UsernamePwAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
public class MultiAuthenticationFilter extends OncePerRequestFilter {


  private final AuthenticationManager authenticationManager;
  private final OtpGenerator otpGenerator;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    // Step1: username & pw
    if(!request.getServletPath().equals("/multiple-auth")){
      return;
    }
    String email = request.getHeader("username");
    String password = request.getHeader("password");
    String otp = request.getHeader("otp");

    if(otp == null){
      // step 1 authenticate user and generate new otp
      Authentication a = new UsernamePwAuthentication(email,password);
      authenticationManager.authenticate(a);
      response.setStatus(204);

    }else{
      // step 2 validate otp
      Authentication a = new UsernameOtpAuthentication(email,otp);
      SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(a));
      filterChain.doFilter(request,response);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    return request.getServletPath().equals("/login") ;
  }
}
