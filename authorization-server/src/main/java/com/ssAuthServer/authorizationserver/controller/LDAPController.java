package com.ssAuthServer.authorizationserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("ldap/authenticate")
public class LDAPController {


  @GetMapping()
  public String getInfo(){
    return "ldap authentication was successful";
  }
}

