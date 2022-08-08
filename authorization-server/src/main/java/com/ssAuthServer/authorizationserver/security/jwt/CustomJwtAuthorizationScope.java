package com.ssAuthServer.authorizationserver.security.jwt;

import com.ssAuthServer.authorizationserver.entities.Authority;
import com.ssAuthServer.authorizationserver.entities.RoleManagement;
import com.ssAuthServer.authorizationserver.security.priviligies.Permission;
import com.ssAuthServer.authorizationserver.security.priviligies.Role;

import java.util.*;
import java.util.stream.Collectors;

public class CustomJwtAuthorizationScope {

  Set<RoleManagement> authorities = new HashSet<>();

  public void setRoleManagements(Set<RoleManagement> authorities){
    this.authorities = authorities;
  }

  public Map<String, Set<Authority>> getScopedAuthorities(){

    return authorities.stream()
        .collect(
            Collectors.groupingBy(scopes -> scopes.getScope().getScopeName(),
                Collectors.mapping(
                    RoleManagement::getAuthority, Collectors.toSet()
                )
            )
        );
  }

  public Set<Permission> getScopedRoles( String authority){
    return Role.getCorrespondingPermissions(authority);


  }

}
