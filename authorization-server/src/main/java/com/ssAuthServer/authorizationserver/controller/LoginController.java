package com.ssAuthServer.authorizationserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
public class LoginController {


  @GetMapping("/custom-login")
  public String login() {
    return "login";
  }

  @PostMapping("/custom-login")
  public String loginFailed() {
    return "redirect:/authenticate?error=invalid username or password";
  }
}

