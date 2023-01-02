package com.ssAuthServer.authorizationserver.security.userdetails;

import com.ssAuthServer.authorizationserver.entities.RoleManagement;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {

  private final RoleManagement roleManagement;

  @Override
  public String getAuthority() {

    String role = "ROLE_" + roleManagement.getAuthority().getRoleName();
    String scope = roleManagement.getClient().getClientId();

    if(!roleManagement.isGlobalEnabled()){
      role += "__" + scope;
    }
    return role;
  }
}
