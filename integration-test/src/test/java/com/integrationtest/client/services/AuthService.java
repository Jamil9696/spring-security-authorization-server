package com.integrationtest.client.services;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class AuthService {

  private static final String KEYCLOAK_URI_PATTERN = "%srealms/%s/protocol/openid-connect/token";
  private static final String KEYCLOAK_TOKEN_GENERATOR = "amber-frontend";

  private final String keycloakUri;
  private final String backendClientPort;
  private String jwt;


  @Autowired
  public AuthService(@Value("${keycloak.auth-server-url}") String keycloakUrl,
                     @Value("${keycloak.realm}") String keycloakRealm,
                     @Value("${testcontainer.pm-service.port}") String port
  ) {
    this.keycloakUri = String.format(KEYCLOAK_URI_PATTERN, keycloakUrl, keycloakRealm);
    this.backendClientPort = port;
  }

  public void invalidateToken() {
    jwt = null;
  }

  // for frontend client
  public Response executeLoginRequest(String username, String password) {

    return given()
        .baseUri(keycloakUri)
        .param("grant_type", "password")
        .param("client_id", KEYCLOAK_TOKEN_GENERATOR)
        .param("username", username)
        .param("password", password)
        .post();
  }

  // extract jwt token from response and return response
  public Response extractJsonWebToken(String username, String password) {

    Response response = executeLoginRequest(username, password);

    if (response.statusCode() == 200) {

      jwt = response.then()
          .extract().response()
          .jsonPath().getString("access_token");
    }

    return response;
  }

  // request for backend rm-service
  public Response executeGetRequest(String endpoint) {

    String url = "http://localhost:" + backendClientPort + endpoint;
    return given()
        .baseUri(url)
        .header(
            "Authorization", "Bearer " + jwt)
        .get();
  }
}
