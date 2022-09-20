package com.ssAuthServer.authorizationserver.security.userdetails;

import com.ssAuthServer.authorizationserver.entities.ResourceUser;
import com.ssAuthServer.authorizationserver.security.priviligies.Role;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class SecurityUser implements UserDetails {

  private ResourceUser resourceUser = new ResourceUser();




  public String getEmail(){
    return resourceUser.getEmail();
  }

  public void setResourceUser(ResourceUser resourceUser){
    this.resourceUser = resourceUser;
  }


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    Map map = Map.of("String", Set.of("TEST1", "TEST2"));

    String string = map.toString();

    System.out.println("MAP STRING: " + string);

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
