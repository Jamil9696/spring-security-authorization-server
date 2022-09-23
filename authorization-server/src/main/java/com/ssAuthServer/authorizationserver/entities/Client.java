package com.ssAuthServer.authorizationserver.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oauth2_registered_client")
public class Client {



  @Column(name = "id")
  private String id;

  @Column(name = "client_authentication_methods")
  private String clientAuthenticationMethods;

  @Id
  @Column(name = "client_id")
  private String clientId;

  @Column(name = "client_id_issued_at")
  private LocalDateTime clientIdIssuedAt;

  @Column(name = "client_name")
  private String name;

  @Column(name = "client_secret")
  private String clientSecret;

  @Column(name = "client_secret_expires_at")
  private LocalDateTime clientSecretExpiresAt;

  @Column(name = "client_settings")
  private String clientSettings;

  @Column(name = "authorization_grant_types")
  private String authorizationGrantTypes;


  @Column(columnDefinition="TEXT", name ="redirect_uris", length = 1024)
  private String redirectUris;


  @Column(columnDefinition="TEXT", name ="scopes", length = 1024)
  private String scopes;


  @Column(columnDefinition="TEXT", name ="token_settings", length = 1024)
  private String tokenSettings;


}
