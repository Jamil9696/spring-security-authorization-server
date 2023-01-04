package com.ssAuthServer.authorizationserver.security.userdetails;

import com.ssAuthServer.authorizationserver.entities.ResourceUser;
import com.ssAuthServer.authorizationserver.repository.ResourceUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private ResourceUserRepo resourceUserRepo;

  @Autowired
  private SecurityUser securityUser;



  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    ResourceUser resourceUser =  resourceUserRepo.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("username could not be found"));
    securityUser.setResourceUser(resourceUser);


    return securityUser;
  }

  public Optional<Long> getId(){
    return Optional.ofNullable(securityUser.getId());
  }

  public Optional<String> getEmail(){
    return Optional.ofNullable(securityUser.getEmail());
  }







}
