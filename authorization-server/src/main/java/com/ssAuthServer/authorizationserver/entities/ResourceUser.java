package com.ssAuthServer.authorizationserver.entities;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "resource_user")
@AllArgsConstructor
@NoArgsConstructor
public class ResourceUser {

  @Id
  @SequenceGenerator(
      name = "user_id_seq",
      sequenceName = "user_id_seq",
      allocationSize = 1)
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "user_id_seq")
  @Column(name = "user_id")
  private long userId;

  @Email
  private String email;

  @Column(name = "user_pw")
  @NotBlank(message = "password must contain a value")
  private String userPw;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  private boolean enabled;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private Set<RoleManagement> roleManagements = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Otp> otps = new ArrayList<>();







}
