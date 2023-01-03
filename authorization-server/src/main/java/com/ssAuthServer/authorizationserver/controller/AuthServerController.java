package com.ssAuthServer.authorizationserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/authenticate")
public class AuthServerController {


  @GetMapping()
  public String getInfo(){
    return "authentication was successful";
  }
}

