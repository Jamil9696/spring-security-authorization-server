package com.ssAuthServer.resourceserver.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class HelloController {



  @GetMapping("/api/hello")
  public String getHello(HttpServletResponse response){
    String name = "Hello Jamil";

    return name;
  }
}
