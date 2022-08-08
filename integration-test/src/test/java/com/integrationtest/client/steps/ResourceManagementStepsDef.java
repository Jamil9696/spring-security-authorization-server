package com.integrationtest.client.steps;

import com.integrationtest.client.services.AuthService;
import com.integrationtest.client.services.ResponseService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

public class ResourceManagementStepsDef {

  @Autowired
  protected ResponseService responseService;

  @Autowired
  private AuthService authService;

  @Given("user is not authenticated")
  public void userIsNotAuthenticated() {
    authService.invalidateToken();
    responseService.currentResponse = null;
  }

  @When("{word} logs in with {word}")
  public void userWantsToLogin(String username, String password) {

    responseService.currentResponse = authService.extractJsonWebToken(username, password);
  }

  @And("calls {word} from pm-management-service")
  public void amberUserCallsApiResourceGetEnvironment(String url) {
    responseService.currentResponse = authService.executeGetRequest(url);
  }
}
