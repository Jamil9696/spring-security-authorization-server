package com.ssAuthServer.authorizationserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class AuthServerController {


  @GetMapping("/multiple-auth")
  public String getInfo(){
    return "authentication was successful";
  }


}

