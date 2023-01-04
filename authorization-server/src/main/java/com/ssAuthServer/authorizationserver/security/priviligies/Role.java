package com.ssAuthServer.authorizationserver.security.priviligies;


import com.ssAuthServer.authorizationserver.entities.RoleManagement;
import com.ssAuthServer.authorizationserver.security.userdetails.SecurityAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static com.ssAuthServer.authorizationserver.security.priviligies.Permission.*;
import static java.util.List.of;

// static Role based Access Control (don't use it, only for demonstration purpose)
// An alternative could be a dynamic concept which is using GrantedAuthority interface (userdetails package)
public enum Role {

    EMPLOYEE(// new permission
        newHashSet(READ_PROJECTS)
    ),

    TEAM_LEADER(
        of( // inherit permissions
            EMPLOYEE.getPermissions()
        ),
        // new permission
        newHashSet(
            READ_RESOURCES,
            ALTER_PROJECTS
        )
    ),

    DEPARTMENT_LEADER(
        of( // inherit permissions
            EMPLOYEE.getPermissions(),
            TEAM_LEADER.getPermissions()),
        // new permission
        newHashSet(
            WRITE_PROJECTS)
    ),

    ADMIN(
        newHashSet(
            DEPARTMENT_LEADER.getPermissions()
        )
    );

    private final Set<Permission> permissions = new HashSet<>();

    Role(HashSet<Permission> newHashSet) {
        permissions.addAll(newHashSet);
    }

    Role(List<Set<Permission>> list, HashSet<Permission> newHashSet) {
        list.forEach(permissions::addAll);
        permissions.addAll(newHashSet);
    }

    public static Set<GrantedAuthority> getGrantedAuthority(Set<RoleManagement> roleManagements){
        return roleManagements.stream().map(SecurityAuthority::new).collect(Collectors.toSet());
    }


    private Set<Permission> getPermissions(){
        return permissions;
    }




}
