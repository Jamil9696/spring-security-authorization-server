package com.ssAuthServer.authorizationserver.security.priviligies;


// static Role based Access Control (don't use it, only for demonstration purpose)
// An alternative could be a dynamic concept which is using GrantedAuthority interface
public enum Permission {

    READ_PROJECTS("project:read"),
    WRITE_PROJECTS("project:write"),
    ALTER_PROJECTS("project:alter"),
    READ_RESOURCES("resources:read"),
    WRITE_RESOURCES("resources:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }


}

