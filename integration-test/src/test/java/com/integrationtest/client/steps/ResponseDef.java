package com.integrationtest.client.steps;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.mtag.ambsrvrm.models.TestRepresentation;
import com.integrationtest.client.services.ResponseService;
import io.cucumber.java.en.Then;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public class ResponseDef {

  private final ResponseService responseService;

  @Autowired
  public ResponseDef(ResponseService responseService) {

    this.responseService = responseService;
  }


  @Then("response is returned with code {int}")
  public void responseIsReturned(int status) {

    assertThat(responseService.currentResponse.getStatusCode(), is(status));
  }

  @Then("response contains list with {int} projects")
  public void responseContainsData(int size) {

    List<TestRepresentation> actual = responseService.currentResponse.jsonPath()
        .getList(".", TestRepresentation.class);
    assertThat(actual.size(), is(size));
  }






}
