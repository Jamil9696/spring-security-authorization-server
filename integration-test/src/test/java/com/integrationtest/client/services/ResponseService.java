package com.integrationtest.client.services;

import io.restassured.response.Response;
import org.springframework.stereotype.Component;
import org.springframework.test.web.reactive.server.StatusAssertions;

@Component
public class ResponseService {

  public StatusAssertions lastStatus;
  public Response currentResponse;


}
