package com.ssAuthServer.authorizationserver.security.priviligies;


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