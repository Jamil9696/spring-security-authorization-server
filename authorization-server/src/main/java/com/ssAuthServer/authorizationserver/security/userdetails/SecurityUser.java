package com.ssAuthServer.authorizationserver.security.userdetails;

import com.ssAuthServer.authorizationserver.entities.ResourceUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class SecurityUser implements UserDetails {

  private ResourceUser resourceUser;


  public String getEmail(){
    return resourceUser.getEmail();
  }

  public Long getId(){
    return resourceUser.getUserId();
  }

  public void setResourceUser(ResourceUser resourceUser){
    this.resourceUser = resourceUser;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return resourceUser
        .getRoleManagements()
        .stream().map(r ->{
          String role = "ROLE_" + r.getAuthority().getRoleName();
          String scope = r.getClient().getClientId();

          if(!r.isGlobalEnabled()){
            role += "__" + scope;
          }
          return new SimpleGrantedAuthority(role);
        })
        .collect(Collectors.toSet());

  }

  @Override
  public String getPassword() {

    return resourceUser.getUserPw();
  }

  @Override
  public String getUsername() {

    return resourceUser.getFirstName() + " " + resourceUser.getLastName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

}
