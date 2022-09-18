package com.ssAuthServer.authorizationserver.security.priviligies;


import com.ssAuthServer.authorizationserver.entities.Authority;
import com.ssAuthServer.authorizationserver.entities.RoleManagement;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.collect.Sets.newHashSet;
import static com.ssAuthServer.authorizationserver.security.priviligies.Permission.*;
import static java.util.List.of;

// Attribute Access Control anschauen
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

    public static Set<SimpleGrantedAuthority> getGrantedAuthorities(Set<RoleManagement> roleManagements){

        return roleManagements
               .stream()
               .flatMap(authority ->
                   Role.valueOf(authority.getAuthority().getRoleName())
                    .getRolesWithPrefix()
                    .stream()
        ).collect(Collectors.toSet());

    }

     public static Set<Permission> getCorrespondingPermissions(String RoleName){
        return Role.valueOf(RoleName).getPermissions();
    }

    private Set<Permission> getPermissions(){
        return permissions;
    }

    private Set<SimpleGrantedAuthority> getRolesWithPrefix(){
        var permissions= getPermissions()
                .stream()
                .map(permission ->
                        new SimpleGrantedAuthority(permission.getPermission())
                )
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;

    }


}
