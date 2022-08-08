package com.ssAuthServer.authorizationserver.security.userdetails;


import com.google.common.collect.Maps;
import com.ssAuthServer.authorizationserver.entities.Authority;
import com.ssAuthServer.authorizationserver.entities.ResourceUser;
import com.ssAuthServer.authorizationserver.entities.RoleManagement;
import com.ssAuthServer.authorizationserver.security.jwt.CustomJwtAuthorizationScope;
import com.ssAuthServer.authorizationserver.security.priviligies.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

  private ResourceUser resourceUser = new ResourceUser();
  private final CustomJwtAuthorizationScope customJwtAuthorizationScope;

  public SecurityUser(CustomJwtAuthorizationScope customJwtAuthorizationScope) {
    this.customJwtAuthorizationScope = customJwtAuthorizationScope;
  }


  public String getEmail(){
    return resourceUser.getEmail();
  }

  public void setResourceUser(ResourceUser resourceUser){
    this.resourceUser = resourceUser;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    // if roles and authorities are scopes
    customJwtAuthorizationScope.setRoleManagements(resourceUser.getRoleManagements());
    // if scopes are not required
    return Role.getGrantedAuthorities(resourceUser.getRoleManagements());

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
